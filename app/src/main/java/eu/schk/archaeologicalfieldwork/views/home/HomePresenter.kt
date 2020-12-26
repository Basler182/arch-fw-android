package eu.schk.archaeologicalfieldwork.views.home

import com.google.firebase.auth.FirebaseAuth
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import eu.schk.archaeologicalfieldwork.views.BasePresenter
import eu.schk.archaeologicalfieldwork.views.BaseView
import eu.schk.archaeologicalfieldwork.views.VIEW
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class HomePresenter (view: BaseView) : BasePresenter(view) {

    fun doAddPlacemark() {
        view?.navigateTo(VIEW.EDIT)
    }

    fun doEditPlacemark(placemark: PlacemarkModel) {
        view?.navigateTo(VIEW.EDIT, 0, "placemark_edit", placemark)
    }

    fun doShowPlacemarksMap() {
       // view?.navigateTo(VIEW.MAPS)
    }

    fun loadPlacemarks() {
        doAsync {
            val placemarks = app.placemarks.findAll()
            uiThread {
                view?.showPlacemarks(placemarks)
            }
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.placemarks.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}