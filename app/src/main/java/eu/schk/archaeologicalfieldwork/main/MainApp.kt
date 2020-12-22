package eu.schk.archaeologicalfieldwork.main

import android.app.Application

import eu.schk.archaeologicalfieldwork.models.firestore.PlacemarkFireStore
import eu.schk.archaeologicalfieldwork.models.firestore.UserFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp : Application(), AnkoLogger {

  lateinit var placemarks: PlacemarkFireStore
  lateinit var user: UserFireStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkFireStore(applicationContext)
    user = UserFireStore(applicationContext)

    info("Placemark started")
  }
}