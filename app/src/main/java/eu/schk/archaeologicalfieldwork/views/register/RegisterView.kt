package eu.schk.archaeologicalfieldwork.views.register

import android.os.Bundle
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_register.*


class RegisterView : BaseView() {

  lateinit var presenter: RegisterPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    presenter = initPresenter(RegisterPresenter(this)) as RegisterPresenter

    lnkLogin.setOnClickListener { presenter.showLogin() }
  }

}