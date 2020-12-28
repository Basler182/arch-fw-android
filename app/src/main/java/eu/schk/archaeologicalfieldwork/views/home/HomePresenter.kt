package eu.schk.archaeologicalfieldwork.views.home

import android.R
import android.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomePresenter(view: BaseView) : BasePresenter(view) {

    fun doAddPlacemark() {
        view?.navigateTo(VIEW.EDIT)
    }

    fun doEditPlacemark(placemark: PlacemarkModel) {
        view?.navigateTo(VIEW.EDIT, 0, "placemark_edit", placemark)
    }

    fun doShowPlacemarksMap() {
       view?.navigateTo(VIEW.MAP)
    }

    fun loadPlacemarks() {
        doAsync {
            val placemarks = app.placemarks.findAll()
            uiThread {
                view?.showPlacemarks(placemarks)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.placemarks.clear()
        view?.navigateTo(VIEW.LOGIN)
    }

  fun doSettings() {
      var message = "Hello"
      val user = FirebaseAuth.getInstance().currentUser
      if (user != null) {
          message += user.displayName + "\n" + "your email is: " + user.email + "!\n \n \n"

          message += "You have an total amount of placemarks: " + app.placemarks.findAll().size +   "\nYou can delete all placemarks or quit settings."
      }

      AlertDialog.Builder(view!!)
          .setTitle("Settings")
          .setMessage(message)
          .setPositiveButton("CANCEL") { dialog, which ->
          }
          .setNegativeButton("Delete all placemarks"){
              _, _ ->
              app.placemarks.clear()
          }
          .setIcon(R.drawable.ic_dialog_info)
          .show()
  }
}