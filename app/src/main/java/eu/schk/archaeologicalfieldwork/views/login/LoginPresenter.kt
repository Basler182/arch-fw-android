package eu.schk.archaeologicalfieldwork.views.login

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkFireStore
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.toast


class LoginPresenter(view: BaseView) : BasePresenter(view) {


  var auth: FirebaseAuth = FirebaseAuth.getInstance()
  var fireStore: PlacemarkFireStore? = null

  init {
    if (app.placemarks is PlacemarkFireStore) {
      fireStore = app.placemarks as PlacemarkFireStore
    }
  }

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchPlacemarks {
            view?.hideProgress()
            view?.navigateTo(VIEW.HOME)
          }
        } else {
          view?.hideProgress()
          view?.navigateTo(VIEW.HOME)
        }
      } else {
        view?.hideProgress()
        view?.toast("Sign Up Failed: ${task.exception?.message}")
      }
    }
  }

  fun showRegister() {
    view?.navigateTo(VIEW.REGISTER)
  }

  fun isLoggedIn() {
    var user = auth.currentUser
    if(user != null) {
      view?.navigateTo(VIEW.HOME)
    }
  }
}