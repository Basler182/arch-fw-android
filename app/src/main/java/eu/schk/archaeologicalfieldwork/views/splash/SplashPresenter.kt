package eu.schk.archaeologicalfieldwork.views.splash

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.doAsync


class SplashPresenter(view: BaseView) : BasePresenter(view) {

  fun doResumeOrLogin(){
    doAsync {
      Thread.sleep(1500)
        view?.navigateTo(VIEW.LOGIN)
      }
    }
  }
