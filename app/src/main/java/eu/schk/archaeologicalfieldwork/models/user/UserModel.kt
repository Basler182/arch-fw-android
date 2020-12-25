package eu.schk.archaeologicalfieldwork.models.user

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    var name: String = "",
    var uid: String = ""
) : Parcelable