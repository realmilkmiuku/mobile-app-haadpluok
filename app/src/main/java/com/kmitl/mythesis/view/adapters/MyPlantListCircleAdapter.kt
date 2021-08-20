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
import com.kmitl.mythesis.view.fragments.HomeFragment
import kotlinx.android.synthetic.main.card_plant_circle.view.*
import kotlinx.android.synthetic.main.card_plant_circle.view.img_user_plant

open class MyPlantListCircleAdapter (
    private val context: Context,
    private var list: ArrayList<Plant>,
    private val fragment: HomeFragment

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_plant_circle,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]

        if(holder is MyViewHolder) {
            GlideLoader(context).loadPlantPictureCircle(model.image, holder.itemView.img_user_plant)
            holder.itemView.tv_plant_name.text = model.plantName

            //holder.itemView.price.text = "$${model.price}"

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