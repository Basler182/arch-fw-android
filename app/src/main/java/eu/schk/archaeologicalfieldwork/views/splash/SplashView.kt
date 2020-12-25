package eu.schk.archaeologicalfieldwork.views.splash

import android.os.Bundle
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView

class SplashView : BaseView(){

  lateinit var presenter: SplashPresenter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
    presenter = initPresenter(SplashPresenter(this)) as SplashPresenter
    presenter.doResumeOrLogin()
  }

  override fun onResume() {
    super.onResume()
    presenter.doResumeOrLogin()
  }

}