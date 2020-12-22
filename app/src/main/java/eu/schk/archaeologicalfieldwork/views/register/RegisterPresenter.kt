package eu.schk.archaeologicalfieldwork.views.register

import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW

class RegisterPresenter (view: BaseView) : BasePresenter(view) {
  fun showLogin() {
    view?.navigateTo(VIEW.LOGIN)
  }
}