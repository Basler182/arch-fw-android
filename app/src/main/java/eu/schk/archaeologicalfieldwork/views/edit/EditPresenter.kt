package eu.schk.archaeologicalfieldwork.views.edit

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import eu.schk.archaeologicalfieldwork.helpers.*
import eu.schk.archaeologicalfieldwork.models.hillfort.Location
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortModel
import eu.schk.archaeologicalfieldwork.views.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread

class EditPresenter (view: BaseView) : BasePresenter(view), AnkoLogger {

  var map: GoogleMap? = null
  var hillfort = HillfortModel()
  var defaultLocation = Location(52.245696, -7.139102, 15f)
  var edit = false;
  var locationManualyChanged = false;
  var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
  private val locationRequest = createDefaultLocationRequest()
  private var tempImagePath : String = ""

  init {
    if (view.intent.hasExtra("placemark_edit")) {
      edit = true
      hillfort = view.intent.extras?.getParcelable<HillfortModel>("placemark_edit")!!
      view.showPlacemark(hillfort)
    } else {
      if (checkLocationPermissions(view)) {
        doSetCurrentLocation()
      }
    }
  }

  @SuppressLint("MissingPermission")
  fun doSetCurrentLocation() {
    locationService.lastLocation.addOnSuccessListener {
      locationUpdate(Location(it.latitude, it.longitude))
    }
  }

  @SuppressLint("MissingPermission")
  fun doResartLocationUpdates() {
    val locationCallback = object : LocationCallback() {
      override fun onLocationResult(locationResult: LocationResult?) {
        if (locationResult != null) {
          val l = locationResult.locations.last()
          if (!locationManualyChanged) {
            locationUpdate(Location(l.latitude, l.longitude))
          }
        }
      }
    }
    if (!edit) {
      locationService.requestLocationUpdates(locationRequest, locationCallback, null)
    }
  }

  override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (isPermissionGranted(requestCode, grantResults)) {
      doSetCurrentLocation()
    } else {
      locationUpdate(defaultLocation)
    }
  }

  fun cachePlacemark (title: String, description: String, rating : Float, comment: String) {
    hillfort.title = title;
    hillfort.description = description
    hillfort.rating = rating
    hillfort.comment = comment
  }

  fun doConfigureMap(m: GoogleMap) {
    map = m
    locationUpdate(hillfort.location)
  }

  fun locationUpdate(location: Location) {
    hillfort.location = location
    hillfort.location.zoom = 15f
    map?.clear()
    val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.location.lat, hillfort.location.lng))
    map?.addMarker(options)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.location.lat, hillfort.location.lng), hillfort.location.zoom))
    view?.showLocation(hillfort.location)
  }

  fun doAddOrSave(title: String, description: String, rating: Float, comment: String) {
    hillfort.title = title
    hillfort.description = description
    hillfort.rating = rating
    hillfort.dateVisited = getDate()
    hillfort.comment = comment
    doAsync {
      if (edit) {
        app.hillforts.update(hillfort)
      } else {
        app.hillforts.create(hillfort)
      }
      uiThread {
        view?.finish()
      }
    }
  }

  fun doCancel() {
    view?.finish()
  }

  fun doDelete() {
    doAsync {
      app.hillforts.delete(hillfort)
      uiThread {
        view?.finish()
      }
    }
  }

  fun doSelectImage() {
    view?.let {
      showImagePicker(view!!, IMAGE_REQUEST)
    }
  }

  fun doSetLocation() {
    locationManualyChanged = true;
    view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.location.lat, hillfort.location.lng, hillfort.location.zoom))
  }

  override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        hillfort.image = data.data.toString()
        view?.showPlacemark(hillfort)
      }
      LOCATION_REQUEST -> {
        val location = data.extras?.getParcelable<Location>("location")!!
        hillfort.location = location
        locationUpdate(location)
      }
      CAMERA_REQUEST ->{
        info("Picture taken")
      }
      SHARE_REQUEST ->{
        info("Placemark shared")
      }
    }
  }

  fun doCamera() {
    view?.let {
      tempImagePath = doCamera(view!!, CAMERA_REQUEST)
    }
  }

  fun doShare(title: String, description: String) {
    val sendIntent: Intent = Intent().apply {
      action = Intent.ACTION_SEND
      putExtra(Intent.EXTRA_TEXT, "Hey! Look at this interesting location I found: $title $description")
      type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    view?.startActivityForResult(shareIntent, SHARE_REQUEST)
  }
}
