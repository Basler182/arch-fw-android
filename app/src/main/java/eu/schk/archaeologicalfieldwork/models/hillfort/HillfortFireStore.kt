package eu.schk.archaeologicalfieldwork.models.hillfort

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import eu.schk.archaeologicalfieldwork.helpers.readImageFromPath
import org.jetbrains.anko.AnkoLogger
import java.io.ByteArrayOutputStream
import java.io.File

const val PATH_STRING_HILLFORTS = "hillforts"
const val PATH_STRING_USER = "users"


class HillfortFireStore(val context: Context) : HillfortStore, AnkoLogger {

  val hillforts = ArrayList<HillfortModel>()
  private var userId: String?
  private lateinit var db: DatabaseReference
  private lateinit var st: StorageReference


  init {
    FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null) fetchHillforts {  }
  }


  override fun findAll(): List<HillfortModel> {
    return hillforts
  }

  override fun findById(id: Long): HillfortModel? {
    return hillforts.find { p -> p.id == id }
  }

  override fun create(hillfort: HillfortModel) {
    val key = userId?.let { db.child(PATH_STRING_USER).child(it).child(PATH_STRING_HILLFORTS).push().key }
    key?.let {
      hillfort.fbId = key
      hillforts.add(hillfort)
      userId?.let { it1 -> db.child(PATH_STRING_USER).child(it1).child(PATH_STRING_HILLFORTS).child(key).setValue(hillfort) }
      updateImage(hillfort)
    }
  }

  override fun update(hillfort: HillfortModel) {
    val foundHillfort: HillfortModel? = hillforts.find { p -> p.fbId == hillfort.fbId }
    if (foundHillfort != null) {
      foundHillfort.title = hillfort.title
      foundHillfort.description = hillfort.description
      foundHillfort.image = hillfort.image
      foundHillfort.location = hillfort.location
      foundHillfort.dateVisited = hillfort.dateVisited
      foundHillfort.rating = hillfort.rating
      foundHillfort.comment = hillfort.comment
    }

    userId?.let { db.child(PATH_STRING_USER).child(it).child(PATH_STRING_HILLFORTS).child(hillfort.fbId).setValue(hillfort) }
    if ((hillfort.image.length) > 0 && (hillfort.image[0] != 'h')) {
      updateImage(hillfort)
    }
  }

  override fun delete(hillfort: HillfortModel) {
    userId?.let { db.child(PATH_STRING_USER).child(it).child(PATH_STRING_HILLFORTS).child(hillfort.fbId).removeValue() }
    hillforts.remove(hillfort)
  }

  override fun clear() {
    hillforts.clear()
  }

  private fun updateImage(hillfort: HillfortModel) {
    if (hillfort.image != "") {
      val fileName = File(hillfort.image)
      val imageName = fileName.getName()

      val imageRef = st.child("$userId/$imageName")
      val baos = ByteArrayOutputStream()
      val bitmap = readImageFromPath(context, hillfort.image)

      bitmap?.let {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val uploadTask = imageRef.putBytes(data)
        uploadTask.addOnFailureListener {
          println(it.message)
        }.addOnSuccessListener { taskSnapshot ->
          taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
            hillfort.image = it.toString()
            userId?.let { it1 -> db.child(PATH_STRING_USER).child(it1).child(PATH_STRING_HILLFORTS).child(hillfort.fbId).setValue(hillfort) }
          }
        }
      }
    }
  }

  fun fetchHillforts(hillfortsReaddy: () -> Unit) {
    val valueEventListener = object : ValueEventListener {
      override fun onCancelled(dataSnapshot: DatabaseError) {
      }
      override fun onDataChange(dataSnapshot: DataSnapshot) {
        dataSnapshot.children.mapNotNullTo(hillforts) { it.getValue(HillfortModel::class.java) }
        hillfortsReaddy()
      }
    }
    userId = FirebaseAuth.getInstance().currentUser!!.uid
    db = FirebaseDatabase.getInstance().reference
    st = FirebaseStorage.getInstance().reference
    hillforts.clear()
    db.child(PATH_STRING_USER).child(userId!!).child(PATH_STRING_HILLFORTS).addListenerForSingleValueEvent(valueEventListener)
  }
}