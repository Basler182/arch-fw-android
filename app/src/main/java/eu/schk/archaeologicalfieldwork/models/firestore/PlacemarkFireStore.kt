package eu.schk.archaeologicalfieldwork.models.firestore

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkStore
import org.jetbrains.anko.AnkoLogger
import java.util.*

class PlacemarkFireStore(val context: Context) : PlacemarkStore, AnkoLogger {

  var placemarks = mutableListOf<PlacemarkModel>()

  private val firestoreInstance = FirebaseFirestore.getInstance()
  private val PLACEMARK_COLLECTION_NAME = "placemark"

  init {

      /**
       * Offline Strategy
       */
      val settings = FirebaseFirestoreSettings.Builder()
          .setPersistenceEnabled(true)
          .setCacheSizeBytes(100048576L)
          .build()
      firestoreInstance.firestoreSettings = settings



    }

  override fun findAll(): List<PlacemarkModel> {
    return placemarks
  }

  override fun create(placemark: PlacemarkModel, user: String) {
    placemark.id = generateRandomId()
    placemark.userCreated = user
    placemarks.add(placemark)
  }

  override fun update(placemark: PlacemarkModel) {
    TODO("Not yet implemented")
  }

  override fun delete(placemark: PlacemarkModel) {
    TODO("Not yet implemented")
  }

  override fun findById(id: Long): PlacemarkModel? {
    TODO("Not yet implemented")
  }

  fun generateRandomId(): Long {
    return Random().nextLong()
  }

}