package eu.schk.archaeologicalfieldwork.main

import android.app.Application
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortFireStore

import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortStore
import org.jetbrains.anko.AnkoLogger


class MainApp : Application(), AnkoLogger {

  lateinit var hillforts: HillfortStore

  override fun onCreate() {
    super.onCreate()
    hillforts = HillfortFireStore(applicationContext)
  }
}