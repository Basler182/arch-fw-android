package eu.schk.archaeologicalfieldwork.views.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eu.schk.archaeologicalfieldwork.R
import eu.schk.archaeologicalfieldwork.models.placemark.PlacemarkModel
import kotlinx.android.synthetic.main.card_placemark.view.*


interface PlacemarkListener {
    fun onPlacemarkClick(placemark: PlacemarkModel)
}

class PlacemarkAdapter constructor(
    private var placemarks: MutableList<PlacemarkModel>,
    private val listener: PlacemarkListener
) : RecyclerView.Adapter<PlacemarkAdapter.MainHolder>() {

    private var placemarksCopy = mutableListOf<PlacemarkModel>().apply { addAll(placemarks) }

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
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(placemark: PlacemarkModel, listener: PlacemarkListener) {
            itemView.placemarkTitle.text = placemark.title
            itemView.description.text = placemark.description
            Glide.with(itemView.context).load(placemark.image).into(itemView.imageIcon);
            itemView.setOnClickListener { listener.onPlacemarkClick(placemark) }
        }
    }

    fun filter(text : String){
        placemarks.clear()
        if(text.isEmpty()) {
            placemarks = mutableListOf<PlacemarkModel>().apply { addAll(placemarksCopy) }
        }
        else{
            for(place in placemarksCopy){
                if(text.contains(place.title, ignoreCase = true)){
                    placemarks.add(place)
                }
            }
        }
        notifyDataSetChanged()
    }
}