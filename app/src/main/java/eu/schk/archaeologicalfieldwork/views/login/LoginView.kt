package eu.schk.archaeologicalfieldwork.views.login

import android.os.Bundle
import android.view.View
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast

class LoginView : BaseView() {

  lateinit var presenter: LoginPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    init(toolbar, false)
    hideProgress()

    presenter = initPresenter(LoginPresenter(this)) as LoginPresenter

    presenter.isLoggedIn()

    logIn.setOnClickListener {
      val email = emailLogin.text.toString()
      val password = passwordLogin.text.toString()
      if (email == "" || password == "") {
        toast("Please provide email + password")
      }
      else {
        presenter.doLogin(email,password)
      }
    }

    lnkRegister.setOnClickListener{presenter.showRegister()}
  }

  override fun showProgress() {
    progressBar.visibility = View.VISIBLE
  }

  override fun hideProgress() {
    progressBar.visibility = View.GONE
  }
}
