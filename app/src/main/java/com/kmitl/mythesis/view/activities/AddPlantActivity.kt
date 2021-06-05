package com.kmitl.mythesis.view.activities

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kmitl.mythesis.R
import com.kmitl.mythesis.databinding.ActivityAddPlantBinding
import com.kmitl.mythesis.utils.Constants

class AddPlantActivity : BaseActivity(), View.OnClickListener {
    private lateinit var btn_back : ImageView
    private lateinit var iv_camera : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        btn_back  = findViewById(R.id.ic_back)
        iv_camera  = findViewById(R.id.iv_camera)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        iv_camera.setOnClickListener(this)

        //add

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {


                R.id.iv_camera -> {

                    // Here we will check if the permission is already allowed or we need to request for it.
                    // First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed
                    if (ContextCompat.checkSelfPermission(
                            this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        //showErrorSnackBar("You already have the storage permission.", false)
                        Constants.showImageChoose(this)

                    } else {

                        /*
                        Request permission to be granted to this application, these permissions
                        must be requested in your manifest, they should not be granted to your app,
                        and they should have protection level
                        */

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
            }
        }
    }




}