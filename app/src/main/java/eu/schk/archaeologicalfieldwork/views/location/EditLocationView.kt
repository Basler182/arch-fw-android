package eu.schk.archaeologicalfieldwork.views.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.models.hillfort.Location
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_map.*


class EditLocationView : BaseView(), GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {

  lateinit var presenter: EditLocationPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    super.init(toolbar, true)

    presenter = initPresenter(EditLocationPresenter(this)) as EditLocationPresenter

    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      it.setOnMarkerDragListener(this)
      it.setOnMarkerClickListener(this)
      presenter.doConfigureMap(it)
    }
  }

  @SuppressLint("SetTextI18n")
  override fun showLocation(location: Location) {
    lat.text = "%.6f".format(location.lat)
    lng.text = "%.6f".format(location.lng)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_edit_location, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_save -> {
        presenter.doSave()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onMarkerDragStart(marker: Marker) {}

  @SuppressLint("SetTextI18n")
  override fun onMarkerDrag(marker: Marker) {
    lat.text = "%.6f".format(marker.position.latitude)
    lng.text = "%.6f".format(marker.position.longitude)
  }

  override fun onMarkerDragEnd(marker: Marker) {
    presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude)
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doUpdateMarker(marker)
    return false
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