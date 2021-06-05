package com.kmitl.mythesis.view.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.User
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private var mUserDetails: User = User()
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURI: String = ""
    private lateinit var btn_back : ImageView
    private lateinit var btn_et_pick_date   : Button
    private lateinit var btn_save           : Button
    private lateinit var et_num             : EditText

    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        btn_et_pick_date    = findViewById<Button>(R.id.btn_et_pick_date)
        btn_save            = findViewById<Button>(R.id.btn_save)
        et_num              = findViewById<EditText>(R.id.et_num)
        btn_back  = findViewById(R.id.ic_back)

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {
            mUserDetails = intent.getParcelableExtra<User>(Constants.EXTRA_USER_DETAILS)!!
        }

        et_username.isEnabled = false
        et_username.setText(mUserDetails.userName)

        et_email.isEnabled = false
        et_email.setText(mUserDetails.email)

        iv_user_photo.setOnClickListener(this@UserProfileActivity)
        btn_save.setOnClickListener(this@UserProfileActivity)


    }

    fun setPickBirthDay() {

        btn_et_pick_date.setOnClickListener {
            val getDate: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)

                val date = formatDate.format(selectDate.time)
                Toast.makeText(this, "Date : " + date, Toast.LENGTH_SHORT).show()
                btn_et_pick_date.text = date


            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

        }

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.iv_user_photo -> {

                    // Here we will check if the permission is already allowed or we need to request for it.
                    // First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE
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

                R.id.btn_save -> {
                    if(validateUserProfileDetails()) {
                        showProgressDialog(resources.getString(R.string.please_wait))

                        if(mSelectedImageFileUri != null)
                        FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                        else {
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails() {
        //<key = string, any =obj>
        val userHashMap = HashMap<String, Any>()

        val mobileNumber = et_num.text.toString().trim { it <= ' ' }

        val birthDay = btn_et_pick_date.text.toString().trim { it <= ' ' }

        val gender =
            if (rd_male.isChecked) {
                Constants.MALE
            } else if (rd_male.isChecked){
                Constants.FEMALE
            } else {
                Constants.LGBTQ
            }

        if(mUserProfileImageURI.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURI
        }

        if (mobileNumber.isNotEmpty()) {
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }

        userHashMap[Constants.GENDER] = gender

        userHashMap[Constants.COMPLETE_PROFILE] = 1
        //showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().updateUserProfileData(this, userHashMap)
    }


    fun userProfileUpdateSuccess() {
        hideProgressDialog()

        Toast.makeText(
                this@UserProfileActivity,
                resources.getString(R.string.msg_profile_update_sucess),
                Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(this@UserProfileActivity, MainActivity::class.java))
        finish()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {

                //showErrorSnackBar("The storage permission is granted", false)
                Constants.showImageChoose(this)
            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityReenter(resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        //the Uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!
                        //!! not be null

                        //iv_user_photo.setImageURI(Uri.parse(selectedImageFileUri.toString()))

                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!, iv_user_photo)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when{
            TextUtils.isEmpty(et_num.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_num), true)
                false
            }

            else -> {
                //showErrorSnackBar(resources.getString(R.string.register_successful), false)
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
        //hideProgressDialog()

        mUserProfileImageURI = imageURL
        updateUserProfileDetails()

    }

}