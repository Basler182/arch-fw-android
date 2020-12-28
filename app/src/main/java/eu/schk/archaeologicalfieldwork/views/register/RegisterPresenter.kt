package eu.schk.archaeologicalfieldwork.views.register

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortFireStore
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.toast


class RegisterPresenter(view: BaseView) : BasePresenter(view) {


    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var fireStore: HillfortFireStore? = null

    init {
      if (app.hillforts is HillfortFireStore) {
        fireStore = app.hillforts as HillfortFireStore
      }
    }

    fun doSignUp(email: String, password: String) {
      view?.showProgress()
      auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(view!!) { task ->
        if (task.isSuccessful) {
          fireStore!!.fetchHillforts {
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