package eu.schk.archaeologicalfieldwork.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlacemarkMapPresenter(view: BaseView) : BasePresenter(view) {

  fun doPopulateMap(map: GoogleMap, placemarks: List<PlacemarkModel>) {
    map.uiSettings.isZoomControlsEnabled = true
    placemarks.forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
    }
  }

  fun doMarkerSelected(marker: Marker) {
    val placemark = marker.tag as PlacemarkModel
    doAsync {
      uiThread {
        view?.showPlacemark(placemark)
      }
    }
  }

  fun loadPlacemarks() {
    doAsync {
      val placemarks = app.placemarks.findAll()
      uiThread {
        view?.showPlacemarks(placemarks)
      }
    }
  }
}