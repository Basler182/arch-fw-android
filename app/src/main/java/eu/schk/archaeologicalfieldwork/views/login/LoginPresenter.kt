package eu.schk.archaeologicalfieldwork.views.login

import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW

class LoginPresenter (view: BaseView) : BasePresenter(view) {
  fun showRegister() {
    view?.navigateTo(VIEW.REGISTER)
  }

}