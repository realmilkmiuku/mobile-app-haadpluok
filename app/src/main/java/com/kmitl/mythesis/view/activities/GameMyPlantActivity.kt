package com.kmitl.mythesis.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kmitl.mythesis.R
import com.kmitl.mythesis.view.fragments.HomeFragment

class GameMyPlantActivity : AppCompatActivity() {

    private val image = "https://cdn.pixabay.com/photo/2018/05/03/21/49/android-3372580_1280.png"

    private lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_my_plant)

        btn_back  = findViewById(R.id.ic_back)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        val imageOne = findViewById<ImageView>(R.id.image_one)
        val imageTwo = findViewById<ImageView>(R.id.image_two)
        val imageThree = findViewById<ImageView>(R.id.image_three)

        Glide.with(this)
            .load(image)
            .into(imageOne)

        Glide.with(this)
            .load(image)
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.img_bg_placeholder)
            .into(imageTwo)

        Glide.with(this)
            .load(image)
            .override(300,400)
            .centerCrop()
            .error(R.drawable.img_bg_placeholder)
            .into(imageThree)


    }
}