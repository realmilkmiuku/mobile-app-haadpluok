package com.kmitl.mythesis.view.activities

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kmitl.mythesis.R
import com.kmitl.mythesis.databinding.ActivityAddPlantBinding
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.kmitl.mythesis.databinding.DialogCustomVeslistBinding
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.utils.GlideLoader
import com.kmitl.mythesis.view.adapters.CustomListVesAdapter
import com.kmitl.mythesis.view.adapters.VegetablesListAdapter
import kotlinx.android.synthetic.main.activity_add_plant.*
import kotlinx.android.synthetic.main.activity_add_plant.iv_camera
import kotlinx.android.synthetic.main.activity_add_plant.iv_user_plant_photo
import kotlinx.android.synthetic.main.activity_plant_details.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddPlantActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityAddPlantBinding
    private var mPlantImageURI : String = ""
    private var mSelectedImageFileUri: Uri? = null
    private  lateinit var mCustomListDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        mBinding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setActionBar()

        val date = getCurrentDateTime()
        val currentDate = date.toString("dd/MM/yyyy")
        mBinding.btnEtAddPlantBday.setHint(currentDate)

        mBinding.etAddPlantName.setOnClickListener(this)
        mBinding.ivCamera.setOnClickListener(this)
        mBinding.etType.setOnClickListener(this)
        mBinding.btnEtAddPlantBday.setOnClickListener(this)
        mBinding.etAddPlantDes.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.ic_back -> {
                    onBackPressed()
                }

                R.id.iv_camera -> {
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

                R.id.btn_et_add_plant_bday -> {
                    pickBirthDay()
                }

                R.id.et_type -> {
                    customVesDialog(resources.getString(R.string.title_select_ves_type),
                    Constants.plantType(),
                    Constants.PLANT_TYPE
                    )
                }

                R.id.btn_save_add_plant -> {
                    if(validateUserPlantDetails()) {
                        uploadPlantImage()
                    }
                }

            }
        }
    }

    private fun setActionBar() {

        setSupportActionBar(toolbar_add_plant)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }

        mBinding.toolbarAddPlant.setNavigationOnClickListener { onBackPressed() }
        mBinding.btnSaveAddPlant.setOnClickListener(this)
    }

    private fun uploadPlantImage() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri, Constants.PLANT_IMG)

    }

    fun imageUploadSuccess(imageURL: String) {
        //hideProgressDialog()
        //showErrorSnackBar("อัปโหลดรูปผักสำเร็จ : $imageURL", false)

        mPlantImageURI = imageURL
        uploadUserPlantDetails()

    }

    private fun uploadUserPlantDetails() {
        val username = this.getSharedPreferences(
            Constants.MYTEHSIS_PREFERENCE, Context.MODE_PRIVATE)
            .getString(Constants.LOGGEN_IN_USERNAME, "")!!

        val userPlant = Plant(
            FirestoreClass().getCurrentUserID(),
            username,
            mBinding.etAddPlantName.text.toString().trim { it <= ' ' },
            mBinding.etType.text.toString().trim { it <= ' ' },
            mBinding.btnEtAddPlantBday.text.toString().trim { it <= ' ' },
            mBinding.etAddPlantDes.text.toString().trim { it <= ' ' },
            mPlantImageURI
        )



        FirestoreClass().uploadUserPlantData(this, userPlant)
    }

    fun userPlantUploadSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@AddPlantActivity,
            resources.getString(R.string.msg_user_plant_crete_success),
            Toast.LENGTH_SHORT
        ).show()

        //startActivity(Intent(this@AddPlantActivity, BottomNavActivity::class.java))
        finish()

    }

    private fun pickBirthDay() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateBday = DatePickerDialog(this ,
                { _, year, month, dayOfMonth ->
                    //Display selected date in TextView
                    mBinding.btnEtAddPlantBday.setText("" + dayOfMonth + "/" + (month.toInt() + 1) + "/" + year)
                }, year, month, day)
            dateBday.show()

        var dateBirthday: DatePickerDialog = dateBday

    }

    private fun java.util.Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): java.util.Date {
        return Calendar.getInstance().time
    }

    fun selectedListItem(item: String, selection: String){
        when(selection){
            Constants.PLANT_TYPE ->{
                mCustomListDialog.dismiss()
                mBinding.etType.setText(item)
            }
        }
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
                showRationDialogPermission()
            }
        }
    }

    private fun showRationDialogPermission() {
        AlertDialog.Builder(this).setMessage("คุณปฎิเสธการเข้าถึงข้อมูลอุปกรณ์ " +
                "หากต้องการใช้งาน เข้าไปที่ตั้งค่า -> แอปพลิเคชันหัดปลูก -> อนุญาตการเข้าถึง")
            .setPositiveButton("ไปที่ตั้งค่า")
            { _,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }

            }
            .setNegativeButton("ยกเลิก"){dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        super.onActivityReenter(resultCode, data)
        if(resultCode == Activity.RESULT_OK){

            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        //the Uri of selected image from phone storage.
                        mSelectedImageFileUri = data.data!!
                        //!! not be null

                        //iv_user_plant_photo.setImageURI(selectedImageFileUri)
                        iv_camera.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))
                        GlideLoader(this).loadAddPlantPicture(mSelectedImageFileUri!!, iv_user_plant_photo)


                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@AddPlantActivity,
                            resources.getString(R.string.err_msg_image_selection_failed),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun customVesDialog(title: String, itemsList: List<String>, selection: String){
        mCustomListDialog = Dialog(this)
        val binding : DialogCustomVeslistBinding = DialogCustomVeslistBinding.inflate(layoutInflater)

        mCustomListDialog.setContentView(binding.root)
        binding.tvTitle.text = title

        binding.rvList.layoutManager = LinearLayoutManager(this)

        val adapter = CustomListVesAdapter(this, itemsList, selection)
        binding.rvList.adapter = adapter
        mCustomListDialog.show()
    }

    private fun validateUserPlantDetails(): Boolean {
        return when{
            TextUtils.isEmpty(mBinding.etAddPlantName.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_user_plant_name), true)
                false
            }

            TextUtils.isEmpty(mBinding.etType.text.toString().trim() { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_user_plant_type), true)
                false
            }

            else -> {

                true
            }
        }
    }




}

