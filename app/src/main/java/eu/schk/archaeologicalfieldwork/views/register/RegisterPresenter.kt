package eu.schk.archaeologicalfieldwork.views.register

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkFireStore
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.toast


class RegisterPresenter(view: BaseView) : BasePresenter(view) {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: PlacemarkFireStore? = null

    init {
      if (app.placemarks is PlacemarkFireStore) {
        fireStore = app.placemarks as PlacemarkFireStore
      }
    }

    fun doSignUp(email: String, password: String) {
      view?.showProgress()
      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
        if (task.isSuccessful) {
          fireStore!!.fetchPlacemarks {
            view?.hideProgress()
            view?.navigateTo(VIEW.HOME)
          }
          view?.hideProgress()
          view?.navigateTo(VIEW.HOME)
        } else {
          view?.hideProgress()
          view?.toast("Sign Up Failed: ${task.exception?.message}")
        }
      }
    }

    fun showLogin() {
        view?.navigateTo(VIEW.LOGIN)
    }
}