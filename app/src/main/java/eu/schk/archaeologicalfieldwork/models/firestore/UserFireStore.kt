package eu.schk.archaeologicalfieldwork.models.firestore

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import eu.schk.archaeologicalfieldwork.models.user.UserModel
import eu.schk.archaeologicalfieldwork.models.user.UserStore
import org.jetbrains.anko.AnkoLogger

class UserFireStore(val context: Context) : UserStore, AnkoLogger {

  private val firestoreInstance = FirebaseFirestore.getInstance()

  var user = UserModel()

  init {
    getUserFromFirestore()
  }

  fun getUserFromFirestore(){

  }

  override fun get(): UserModel {
    return user
  }

  override fun create(userStore: UserStore) {
    TODO("Not yet implemented")
  }

  override fun update(userStore: UserStore) {
    TODO("Not yet implemented")
  }


}