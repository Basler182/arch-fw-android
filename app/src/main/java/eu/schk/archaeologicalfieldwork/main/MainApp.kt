package eu.schk.archaeologicalfieldwork.main

import android.app.Application
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkFireStore

import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

  lateinit var placemarks: PlacemarkStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkFireStore(applicationContext)

    info("Placemark started")
  }
}