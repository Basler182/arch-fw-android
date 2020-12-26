package eu.schk.archaeologicalfieldwork.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun getDate(): String {
  val sdf = SimpleDateFormat("dd/M/yyyy")
  return sdf.format(Date())
}