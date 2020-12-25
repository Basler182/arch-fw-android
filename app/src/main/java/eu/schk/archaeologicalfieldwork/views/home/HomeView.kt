package eu.schk.archaeologicalfieldwork.views.home

import android.os.Bundle
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.views.BaseView

import kotlinx.android.synthetic.main.activity_login.*

class HomeView : BaseView() {

  lateinit var presenter: HomePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    setSupportActionBar(toolbar)
    super.init(toolbar, false)


    presenter = initPresenter(HomePresenter(this)) as HomePresenter

  }

}