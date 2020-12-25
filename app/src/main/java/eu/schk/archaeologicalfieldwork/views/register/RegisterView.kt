package eu.schk.archaeologicalfieldwork.views.register

import android.os.Bundle
import android.view.View
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast


class RegisterView : BaseView() {

  lateinit var presenter: RegisterPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_register)
    init(toolbarRegister, false)
    hideProgress()

    presenter = initPresenter(RegisterPresenter(this)) as RegisterPresenter

    signUp.setOnClickListener {
      val email = emailRegister.text.toString()
      val password = passwordRegister.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doSignUp(email,password)
      }
    }

    lnkLogin.setOnClickListener{presenter.showLogin()}
  }

  override fun showProgress() {
    progressBarRegister.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBarRegister.visibility = View.GONE
  }
}
