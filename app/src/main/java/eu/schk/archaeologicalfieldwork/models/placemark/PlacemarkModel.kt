package eu.schk.archaeologicalfieldwork.models.placemark

import android.os.Parcelable
import eu.schk.archaeologicalfieldwork.models.location.LocationModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlacemarkModel(
    var id : Long = 0,
    var title: String = "",
    var description: String = "",
    var image: String = "",
    var visited: Boolean = false,
    var dateVisited: String = "",
    var userCreated: String = "",
    var isFavorite : Boolean = false,
    var rating : Rating = Rating.UNRATED,
    var location: LocationModel = LocationModel()
) : Parcelable


enum class Rating {
  ONESTAR, TWOSTART, THREESTAR, FOURSTAR, FIVESTAR, UNRATED
}