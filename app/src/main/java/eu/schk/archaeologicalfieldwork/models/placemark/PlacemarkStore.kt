package eu.schk.archaeologicalfieldwork.models.placemark

interface PlacemarkStore {
  fun findAll(): List<PlacemarkModel>
  fun create(placemark: PlacemarkModel)
  fun update(placemark: PlacemarkModel)
  fun delete(placemark: PlacemarkModel)
  fun findById(id:Long) : PlacemarkModel?
  fun clear()
}