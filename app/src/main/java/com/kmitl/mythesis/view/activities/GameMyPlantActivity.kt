package com.kmitl.mythesis.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.view.fragments.HomeFragment

class GameMyPlantActivity : AppCompatActivity() {
    private lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_my_plant)

        btn_back  = findViewById(R.id.ic_back)

        btn_back.setOnClickListener {
            onBackPressed()
        }



    }
}