package eu.schk.archaeologicalfieldwork.views

import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.views.home.HomeView
import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.placemark.Location
import eu.schk.archaeologicalfieldwork.views.edit.EditView
import eu.schk.archaeologicalfieldwork.views.location.EditLocationView
import eu.schk.archaeologicalfieldwork.views.login.LoginView
import eu.schk.archaeologicalfieldwork.views.map.PlacemarkMapView
import eu.schk.archaeologicalfieldwork.views.register.RegisterView
import org.jetbrains.anko.AnkoLogger


const val IMAGE_REQUEST = 1
const val LOCATION_REQUEST = 2
const val CAMERA_REQUEST = 3

enum class VIEW {
  HOME, REGISTER, LOGIN, EDIT, LOCATION, MAP
}

abstract class BaseView() : AppCompatActivity(), AnkoLogger {

  var basePresenter: BasePresenter? = null

  fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
    val intent: Intent = when (view) {
      VIEW.HOME -> Intent(this, HomeView::class.java)
      VIEW.REGISTER -> Intent(this, RegisterView::class.java)
      VIEW.LOGIN -> Intent(this, LoginView::class.java)
      VIEW.EDIT -> Intent(this, EditView::class.java)
      VIEW.LOCATION -> Intent(this, EditLocationView::class.java)
      VIEW.MAP -> Intent(this, PlacemarkMapView::class.java)
    }
    if (key != "") {
      intent.putExtra(key, value)
    }
    startActivityForResult(intent, code)
  }

  fun init(toolbar: Toolbar, upEnabled: Boolean) {
    toolbar.title = title
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
    val user = FirebaseAuth.getInstance().currentUser
    if (user != null) {
      toolbar.title = "${title}: ${user.email}"
    }
  }

  fun initPresenter(presenter: BasePresenter): BasePresenter {
    basePresenter = presenter
    return presenter
  }

  fun init(toolbar: Toolbar) {
    toolbar.title = title
    setSupportActionBar(toolbar)
  }

  override fun onDestroy() {
    basePresenter?.onDestroy()
    super.onDestroy()
  }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (data != null) {
      basePresenter?.doActivityResult(requestCode, resultCode, data)
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
  }

  open fun showPlacemark(placemark: PlacemarkModel) {}
  open fun showPlacemarks(placemarks: List<PlacemarkModel>) {}
  open fun showLocation(location : Location) {}
  open fun showProgress() {}
  open fun hideProgress() {}
}