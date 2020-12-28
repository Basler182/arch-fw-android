package eu.schk.archaeologicalfieldwork.views.home

import android.R
import android.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortModel
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HomePresenter(view: BaseView) : BasePresenter(view) {

    fun doAddHillfort() {
        view?.navigateTo(VIEW.EDIT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.EDIT, 0, "placemark_edit", hillfort)
    }

    fun doShowHillfortsMap() {
       view?.navigateTo(VIEW.MAP)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showPlacemarks(hillforts)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }

  fun doSettings() {
      var message = "Hello"
      var hillforts = app.hillforts.findAll()
      var countVisited = 0
      for(hillfort in hillforts){
          if(hillfort.dateVisited.isNotEmpty()){
              countVisited++
          }
      }
      val user = FirebaseAuth.getInstance().currentUser
      if (user != null) {
          message += user.displayName + "\n" + "your email is: " + user.email + "!\n \n \n"

          message += "You have an total amount of hillforts: " + hillforts.size +  "\n And you visited: $countVisited hillforts already. \n"+  "\nYou can delete all hillforts or quit settings."
      }

      AlertDialog.Builder(view!!)
          .setTitle("Settings")
          .setMessage(message)
          .setPositiveButton("CANCEL") { _, _ ->
          }
          .setNegativeButton("Delete all hillforts"){
              _, _ ->
              app.hillforts.clear()
          }
          .setIcon(R.drawable.ic_dialog_info)
          .show()
  }
}