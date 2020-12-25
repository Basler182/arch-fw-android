package eu.schk.archaeologicalfieldwork.models.user

interface UserStore {
  fun get(): UserModel
  fun create(userModel: UserModel)
  fun update(userModel: UserModel)
}