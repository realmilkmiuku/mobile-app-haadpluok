package com.kmitl.mythesis.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.kmitl.mythesis.R

class AddTaskActivity : AppCompatActivity() {
    private lateinit var btn_back : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        btn_back  = findViewById(R.id.ic_back)

        btn_back.setOnClickListener {
            onBackPressed()
        }

    }
}