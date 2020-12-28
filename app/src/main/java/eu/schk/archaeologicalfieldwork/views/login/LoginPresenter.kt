package eu.schk.archaeologicalfieldwork.views.login

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortFireStore
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.toast


class LoginPresenter(view: BaseView) : BasePresenter(view) {


  var auth: FirebaseAuth = FirebaseAuth.getInstance()
  var fireStore: HillfortFireStore? = null

  init {
    if (app.hillforts is HillfortFireStore) {
      fireStore = app.hillforts as HillfortFireStore
    }
  }

  fun doLogin(email: String, password: String) {
    view?.showProgress()
    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
      if (task.isSuccessful) {
        if (fireStore != null) {
          fireStore!!.fetchHillforts {
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
      view?.hideProgress()
      view?.navigateTo(VIEW.HOME)
    }
  }

  fun showRegister() {
    view?.navigateTo(VIEW.REGISTER)
  }

  fun isLoggedIn() {
    val user = auth.currentUser?.uid
    if(user != null && fireStore != null) {
      view?.navigateTo(VIEW.HOME)
    }
  }
}