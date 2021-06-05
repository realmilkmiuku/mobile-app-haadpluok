package com.kmitl.mythesis.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.kmitl.mythesis.R


class SplashActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        /* hide nav device bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        */

        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                //Launch the Main Activity
                startActivity(Intent(this@SplashActivity, BottomNavActivity::class.java))
                finish() // Call this when your activity is done and should be close
            },
            1500 // Time second from SplashActivity move to MainActivity
        )

    }
}