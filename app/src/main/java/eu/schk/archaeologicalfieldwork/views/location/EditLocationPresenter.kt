package eu.schk.archaeologicalfieldwork.views.location

import android.content.Intent
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import eu.schk.archaeologicalfieldwork.models.placemark.Location
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView


class EditLocationPresenter(view: BaseView) : BasePresenter(view) {

  var location = Location()

  init {
    location = view.intent.extras?.getParcelable("location")!!
  }

  fun doConfigureMap(map: GoogleMap) {
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Placemark")
        .snippet("GPS : $loc")
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
    view?.showLocation(location);
  }

  fun doUpdateLocation(lat: Double, lng: Double) {
    location.lat = lat
    location.lng = lng
  }

  fun doSave() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    view?.setResult(0, resultIntent)
    view?.finish()
  }

  fun doUpdateMarker(marker: Marker) {
    val loc = LatLng(location.lat, location.lng)
    marker.snippet = "GPS : $loc"
  }
}