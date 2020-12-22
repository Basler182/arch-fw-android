package eu.schk.archaeologicalfieldwork.models.user

import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel

interface UserStore {
  fun get(): UserModel
  fun create(userStore: UserStore)
  fun update(userStore: UserStore)
}