package eu.schk.archaeologicalfieldwork.views.edit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.helpers.getDate
import eu.schk.archaeologicalfieldwork.models.placemark.Location
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class EditView : BaseView(), AnkoLogger {

  lateinit var presenter: EditPresenter
  var placemark = PlacemarkModel()
  lateinit var map: GoogleMap

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_edit)

    super.init(toolbarAdd, true);

    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      presenter.doConfigureMap(map)
      it.setOnMapClickListener { presenter.doSetLocation() }
    }

    presenter = initPresenter (EditPresenter(this)) as EditPresenter

    chooseImage.setOnClickListener {
      chooseImage()
    }

    chooseCamera.setOnClickListener {
      presenter.cachePlacemark(placemarkTitle.text.toString(), description.text.toString(), ratingBar.rating)
      presenter.doCamera()
    }

    shareButton.setOnClickListener {
      if(placemarkTitle.text.isNotEmpty() && description.text.isNotEmpty()) presenter.doShare(placemarkTitle.text.toString(), description.text.toString() )
      else{
        toast("To share the placemark, it needs a title and description")
      }
    }

    checkBoxVisited.setOnClickListener {
      if (checkBoxVisited.isChecked){
        tv_date.text = getDate()
      }else{
        tv_date.text = ""
      }
    }
  }

  private fun chooseImage() {
    presenter.cachePlacemark(placemarkTitle.text.toString(), description.text.toString(), ratingBar.rating)
    presenter.doSelectImage()
  }

  override fun showPlacemark(placemark: PlacemarkModel) {
    if (placemarkTitle.text.isEmpty()) placemarkTitle.setText(placemark.title)
    if (description.text.isEmpty())  description.setText(placemark.description)
    if(tv_date.text.isEmpty()) tv_date.text = placemark.dateVisited
    ratingBar.rating = placemark.rating
    checkBoxVisited.isChecked = placemark.dateVisited.isNotEmpty()

    Glide.with(this).load(placemark.image).into(placemarkImage);

    chooseImage.text = getString(R.string.change_image)
    this.showLocation(placemark.location)
  }

  @SuppressLint("SetTextI18n")
  override fun showLocation (location : Location) {
    lat.text = "%.6f".format(location.lat)
    lng.text = "%.6f".format(location.lng)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    if (presenter.edit) menu.getItem(0).isVisible = true
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_delete -> {
        presenter.doDelete()
      }
      R.id.item_save -> {
        if (placemarkTitle.text.toString().isEmpty()) {
          toast("Please enter placemark title")
        } else {
          presenter.doAddOrSave(placemarkTitle.text.toString(), description.text.toString(), ratingBar.rating)
        }
      }
      R.id.item_cancel -> {
        finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      presenter.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onBackPressed() {
    presenter.doCancel()
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
    presenter.doResartLocationUpdates()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}