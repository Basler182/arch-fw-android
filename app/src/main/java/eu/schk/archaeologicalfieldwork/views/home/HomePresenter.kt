package eu.schk.archaeologicalfieldwork.views.home

import android.util.Log
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
       view?.navigateTo(VIEW.MAP)
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

    fun debug() {
        val marks = app.placemarks.findAll()
        for(mark in marks){
            Log.wtf("Mark", mark.toString())
        }
    }
}