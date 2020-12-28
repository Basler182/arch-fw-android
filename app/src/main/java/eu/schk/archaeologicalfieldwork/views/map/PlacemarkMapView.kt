package eu.schk.archaeologicalfieldwork.views.map

import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortModel
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_placemark_maps.*


class PlacemarkMapView : BaseView(), GoogleMap.OnMarkerClickListener {

  lateinit var presenter: PlacemarkMapPresenter
  lateinit var map : GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_maps)
    super.init(toolbar, true);

    presenter = initPresenter (PlacemarkMapPresenter(this)) as PlacemarkMapPresenter

    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      map.setOnMarkerClickListener(this)
      presenter.loadPlacemarks()
    }
  }

  override fun showPlacemark(hillfort: HillfortModel) {
    currentTitle.text = hillfort.title
    currentDescription.text = hillfort.description
    Glide.with(this).load(hillfort.image).into(currentImage);
  }

  override fun showPlacemarks(hillforts: List<HillfortModel>) {
    presenter.doPopulateMap(map, hillforts)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doMarkerSelected(marker)
    return true
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}