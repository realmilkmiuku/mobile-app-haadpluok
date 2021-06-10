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
}
