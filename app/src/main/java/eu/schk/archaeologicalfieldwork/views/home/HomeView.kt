package eu.schk.archaeologicalfieldwork.views.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortModel
import eu.schk.archaeologicalfieldwork.views.BaseView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.toolbar

class HomeView : BaseView(), PlacemarkListener {

  lateinit var presenter: HomePresenter

  lateinit var adapter : PlacemarkAdapter


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    setSupportActionBar(toolbar)
    super.init(toolbar, false)

    presenter = initPresenter(HomePresenter(this)) as HomePresenter

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    presenter.loadHillforts()
  }

  override fun showPlacemarks(hillforts: List<HillfortModel>) {

    adapter = PlacemarkAdapter(hillforts.toMutableList(), this)

    recyclerView.adapter = adapter
    recyclerView.adapter?.notifyDataSetChanged()

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter(query.toString())
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter(newText.toString())
        return true
      }
    })

    switchFavorites.setOnClickListener {
        adapter.showFavorites(switchFavorites.isChecked)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.item_add -> presenter.doAddHillfort()
      R.id.item_map -> presenter.doShowHillfortsMap()
      R.id.item_logout -> presenter.doLogout()
      R.id.item_settings -> presenter.doSettings()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onPlacemarkClick(hillfort: HillfortModel) {
    presenter.doEditHillfort(hillfort)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    presenter.loadHillforts()
    super.onActivityResult(requestCode, resultCode, data)
  }
}