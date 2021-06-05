package com.kmitl.mythesis.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.kmitl.mythesis.R
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.view.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var tv_main : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_main = findViewById<TextView>(R.id.tv_main)

        val sharedPreferences = getSharedPreferences(Constants.MYTEHSIS_PREFERENCE, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGEN_IN_USERNAME, " ")!!
        tv_main.text = "Hello $username."

    }

}