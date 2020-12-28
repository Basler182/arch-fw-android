package eu.schk.archaeologicalfieldwork.models.hillfort

interface HillfortStore {
  fun findAll(): List<HillfortModel>
  fun create(hillfort: HillfortModel)
  fun update(hillfort: HillfortModel)
  fun delete(hillfort: HillfortModel)
  fun findById(id:Long) : HillfortModel?
  fun clear()
}