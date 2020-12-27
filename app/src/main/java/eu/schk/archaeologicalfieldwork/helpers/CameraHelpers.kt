package eu.schk.archaeologicalfieldwork.helpers

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import eu.schk.archaeologicalfieldwork.views.BaseView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

lateinit var imagePath: String


@SuppressLint("QueryPermissionsNeeded")
fun doCamera(view: BaseView, request : Int) : String{
  Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
    takePictureIntent.resolveActivity(view.packageManager)?.also {
      val photoFile: File? = try {
        createImageFile(view)
      } catch (ex: IOException) {
        null
      }
      photoFile?.also {
        view.startActivityForResult(takePictureIntent, request)
      }
    }
  }
  return imagePath
}

@SuppressLint("SimpleDateFormat")
private fun createImageFile(view : BaseView): File {
  val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
  val storageDir: File? = view.applicationContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
  return File.createTempFile(
      "JPEG_${timeStamp}_",
      ".jpg",
      storageDir
  ).apply {
    imagePath = absolutePath
  }
}