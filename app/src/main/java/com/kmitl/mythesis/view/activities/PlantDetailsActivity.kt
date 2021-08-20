package com.kmitl.mythesis.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.utils.GlideLoader
import com.kmitl.mythesis.view.fragments.AllPlantFragment

import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_plant_details.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class PlantDetailsActivity : BaseActivity(), View.OnClickListener {
    private var mPlantId: String = ""
    lateinit var allPlantFragment : AllPlantFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_details)
        setActionBar()

        if (intent.hasExtra(Constants.EXTRA_PLANT_ID)) {
            mPlantId = intent.getStringExtra(Constants.EXTRA_PLANT_ID)!!
            Log.i("plant id", mPlantId)
        }

        getPlantDetails()
    }

    private fun setActionBar() {

        setSupportActionBar(toolbar_plant_details)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }

        toolbar_plant_details.setNavigationOnClickListener { onBackPressed() }

        btn_delete_plant.setOnClickListener(this)
    }

    private fun getPlantDetails() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getPlantDetails(this, mPlantId)
    }

    fun plantDetailsSuccess(plant: Plant) {
        hideProgressDialog()
        GlideLoader(this@PlantDetailsActivity).loadPlantPicture(
            plant.image,
            iv_user_plant_photo
        )

        tv_plant_name_detail.text   = plant.plantName
        tv_type_detail.text         = plant.plantType
        tv_plant_bday_detail.text   = plant.plantBday
        tv_plant_des_detail.text    = plant.plantDes

        btn_delete_plant.setOnClickListener{

        }

    }



}
