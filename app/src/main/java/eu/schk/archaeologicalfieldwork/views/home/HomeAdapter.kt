package eu.schk.archaeologicalfieldwork.views.home

import android.graphics.Color.GREEN
import android.graphics.Color.WHITE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.models.hillfort.HillfortModel
import kotlinx.android.synthetic.main.card_placemark.view.*


interface PlacemarkListener {
    fun onPlacemarkClick(hillfort: HillfortModel)
}

class PlacemarkAdapter constructor(
    private var hillforts: MutableList<HillfortModel>,
    private val listener: PlacemarkListener
) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    private var hillfortsCopy = mutableListOf<HillfortModel>().apply { addAll(hillforts) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_placemark,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = hillforts[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: PlacemarkListener) {
            itemView.placemarkTitle.text = hillfort.title
            itemView.description.text = hillfort.description
            Glide.with(itemView.context).load(hillfort.image).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onPlacemarkClick(hillfort) }
        }
    }

    fun filter(text : String){
        hillforts.clear()
        if(text.isEmpty()) {
            hillforts = mutableListOf<HillfortModel>().apply { addAll(hillfortsCopy) }
        }
        else{
            for(place in hillfortsCopy){
                if(text.contains(place.title, ignoreCase = true)){
                    hillforts.add(place)
                }
            }
        }
        notifyDataSetChanged()
    }

    fun showFavorites(show: Boolean){
        hillforts.clear()
        if(show){
            for(place in hillfortsCopy){
                if(place.rating > 3){
                    hillforts.add(place)
                }
            }
        }else{
            hillforts = mutableListOf<HillfortModel>().apply { addAll(hillfortsCopy) }
        }
        notifyDataSetChanged()
    }
}