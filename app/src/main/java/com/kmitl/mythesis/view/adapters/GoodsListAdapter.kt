package com.kmitl.mythesis.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.models.Goods
import com.kmitl.mythesis.utils.GlideLoader
import com.kmitl.mythesis.view.fragments.SearchFragment
import kotlinx.android.synthetic.main.card_goods_suggest.view.*

open class GoodsListAdapter (
    private val context: Context,
    private var list: ArrayList<Goods>,
    private val fragment: SearchFragment

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_goods_suggest,
                parent,
                false
            )

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = list[position]

        if(holder is MyViewHolder) {
            GlideLoader(context).loadPlantPicture(model.image, holder.itemView.img)
            holder.itemView.tv_name.text = model.goods_name
            holder.itemView.tv_price.text = model.goods_price

            //holder.itemView.price.text = "$${model.price}"



        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}