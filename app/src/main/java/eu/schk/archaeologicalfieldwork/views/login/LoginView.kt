package eu.schk.archaeologicalfieldwork.views.login

import android.os.Bundle
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter
    lnkRegister.setOnClickListener { presenter.showRegister() }
  }

}
