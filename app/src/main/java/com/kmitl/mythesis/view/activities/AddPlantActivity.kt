package com.kmitl.mythesis.view.activities

import android.app.Dialog
import android.os.Bundle

import android.view.View
import android.widget.Toast

import com.karumi.dexter.Dexter
import com.kmitl.mythesis.R
import com.kmitl.mythesis.databinding.ActivityAddPlantBinding
import com.kmitl.mythesis.databinding.DialogCustomImgSelectionBinding

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable

import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.kmitl.mythesis.databinding.DialogCustomVeslistBinding
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.AddPlant
import com.kmitl.mythesis.models.User
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.view.adapters.CustomListVesAdapter
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddPlantActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityAddPlantBinding
    private var mImagePath : String = ""
    private  lateinit var mCustomListDialog: Dialog
    private var formatDate = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        mBinding = ActivityAddPlantBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.icBack.setOnClickListener(this)
        mBinding.etAddPlantName.setOnClickListener(this)
        mBinding.ivCamera.setOnClickListener(this)
        mBinding.etType.setOnClickListener(this)
        mBinding.btnEtAddPlantBday.setOnClickListener(this)
        mBinding.etAddPlantDes.setOnClickListener(this)
        mBinding.btnSaveAddPlant.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.ic_back -> {
                    onBackPressed()
                    return
                }

                R.id.iv_camera -> {
                    customimageSelectionDialog()
                    return
                }

                R.id.btn_et_add_plant_bday -> {
                    setPickBirthDay()
                    return
                }

                R.id.et_type -> {
                    customVesDialog(resources.getString(R.string.title_select_ves_type),
                    Constants.plantType(),
                    Constants.USER_VES_TYPE
                    )
                    return
                }

                R.id.btn_save_add_plant -> {
                    val nPlant      = mBinding.etAddPlantName.text.toString().trim(){ it <= ' ' }
                    val bDayPlant   = mBinding.btnEtAddPlantBday.text.toString().trim(){ it <= ' ' }
                    val desPlant    = mBinding.etAddPlantDes.text.toString().trim(){ it <= ' ' }

                    when {
                        TextUtils.isEmpty(mImagePath) -> {
                            Toast.makeText(
                                this@AddPlantActivity,
                                resources.getString(R.string.err_msg_image_selection_failed),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                        TextUtils.isEmpty(nPlant) -> {
                            Toast.makeText(
                                this@AddPlantActivity,
                                resources.getString(R.string.err_msg_user_plant_name),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                this@AddPlantActivity,
                                "save จ้า",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }


                    }
                }

            }
        }
    }

    private fun setPickBirthDay() {

            val getDate: Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH, i3)

                val date = formatDate.format(selectDate.time)
                Toast.makeText(this, "Date : " + date, Toast.LENGTH_SHORT).show()
                mBinding.btnEtAddPlantBday.text = date


            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()

    }

    private fun customimageSelectionDialog() {
        val dialog = Dialog(this)
        val binding: DialogCustomImgSelectionBinding =
            DialogCustomImgSelectionBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvCamera.setOnClickListener {
            Dexter.withContext(this@AddPlantActivity).withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationDialogPermission()
                }

            }).onSameThread().check()

            dialog.dismiss()
        }

        binding.tvGallery.setOnClickListener {
            Dexter.withContext(this@AddPlantActivity)
                .withPermission(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                ).withListener(object :PermissionListener{
                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                       val galleryIntent = Intent(Intent.ACTION_PICK,
                       MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(galleryIntent, GALLERY)
                    }

                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(
                            this@AddPlantActivity,
                            "ปฎิเสธการเข้าถึงอัลบัมรูป",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        showRationDialogPermission()
                    }

                }).onSameThread()
                .check()
            //END
            dialog.dismiss()
        }

        dialog.show()
    }

    fun selectedListItem(item: String, selection: String){
        when(selection){
            Constants.USER_VES_TYPE ->{
                mCustomListDialog.dismiss()
                mBinding.etType.setText(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA){
                data?.extras?.let{
                    val thumbnail : Bitmap = data.extras?.get("data") as Bitmap

                    //mBinding.ivUserPlantPhoto.setImageBitmap(thumbnail)

                    Glide.with(this)
                        .load(thumbnail)
                        .centerCrop()
                        .placeholder(R.drawable.img_plant_placeholder)
                        .into(mBinding.ivUserPlantPhoto)

                    mImagePath = saveImageToInternalStorage(thumbnail)

                    mBinding.ivCamera.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))

                    Log.i("ImagePath", mImagePath)

                }
            }

            if(requestCode == GALLERY){
                data?.let{
                    val selectedPhotoUri = data.data

                    //mBinding.ivUserPlantPhoto.setImageURI(selectedPhotoUri)

                    Glide.with(this)
                        .load(selectedPhotoUri)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object  : RequestListener<Drawable>{
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                Log.e("TAG", "การแสดงรูปผิดพลาด", e)
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                resource?.let{
                                    val bitmap: Bitmap = resource?.toBitmap()
                                    mImagePath = saveImageToInternalStorage(bitmap)
                                    Log.i("ImagePath", mImagePath)
                                }
                                return false
                            }

                        })
                        .placeholder(R.drawable.img_plant_placeholder)
                        .into(mBinding.ivUserPlantPhoto)

                    mBinding.ivCamera.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_vector_edit))

                }
            }
        } else if(resultCode == Activity.RESULT_CANCELED){
            Log.e("ยกเลิก","ยกเลิกการเพิ่มรูปภาพ")
        }

    }

    private fun showRationDialogPermission() {
        AlertDialog.Builder(this).setMessage("คุณไม่ได้อนุญาตการเข้าถึงข้อมูลอุปกรณ์ของคุณ " +
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

    private fun saveImageToInternalStorage(bitmap: Bitmap):String{
        val wrapper = ContextWrapper(applicationContext)

        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException) {
            e.printStackTrace()
        }

        return file.absolutePath
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

    companion object{
        private const val CAMERA = 1
        private const val GALLERY = 2

        private const val IMAGE_DIRECTORY = "UserPlantImage"
    }
}

