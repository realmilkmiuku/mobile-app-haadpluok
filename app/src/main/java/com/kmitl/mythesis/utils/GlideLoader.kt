package com.kmitl.mythesis.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.kmitl.mythesis.R
import java.io.IOException

class GlideLoader(val context: Context) {

    fun loadUserPicture(imageURI: Uri, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(imageURI) // URI of the image
                .fitCenter() // Scale type of the image
                .circleCrop()
                .placeholder(R.drawable.img_bg_placeholder) //A default place holder if img is failed to load
                .into(imageView) // the view in which the img will be load

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadAddPlantPicture(imageURI: Uri, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(imageURI)
                .override(400,400)
                .centerCrop()
                .placeholder(R.drawable.img_plant_placeholder)
                .into(imageView)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadPlantPicture(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image)
                //.override(400,400)
                .centerCrop()
                .into(imageView)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun loadPlantPictureCircle(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image)
                .fitCenter() // Scale type of the image
                .circleCrop()
                .into(imageView)

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
