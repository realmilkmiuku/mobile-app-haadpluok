package com.kmitl.mythesis.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.utils.GlideLoader
import com.kmitl.mythesis.view.activities.PlantDetailsActivity
import com.kmitl.mythesis.view.fragments.AllPlantFragment
import kotlinx.android.synthetic.main.card_plant_rectangle.view.img_user_plant
import kotlinx.android.synthetic.main.card_plant_rectangle.view.tv_plant_name
import kotlinx.android.synthetic.main.card_plant_rectangle.view.*


open class MyPlantListRectangleAdapter (
    private val context: Context,
    private var list: ArrayList<Plant>,
    private val fragment: AllPlantFragment

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_plant_rectangle,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadPlantPicture(model.image, holder.itemView.img_user_plant)
            holder.itemView.tv_plant_name.text = model.plantName

            //holder.itemView.price.text = "$${model.price}"

            holder.itemView.ic_delete_plant.setOnClickListener {
                // TODO delete plant
                fragment.deletePlant(model.plant_id)
            }

            holder.itemView.img_user_plant.setOnClickListener {
                val intent = Intent(context, PlantDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PLANT_ID, model.plant_id)
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}