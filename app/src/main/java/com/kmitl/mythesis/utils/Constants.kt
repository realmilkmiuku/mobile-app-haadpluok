package com.kmitl.mythesis.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.google.type.Date

object Constants {
    //ตาราง
    const val USERS: String = "users"
    const val USER_PLANT: String = "user_plant"
    const val INFO_VEGETABLES: String = "info_vegetables"

    const val MYTEHSIS_PREFERENCE: String   = "MyThesisPrefs"
    const val LOGGEN_IN_USERNAME: String    = "logged_in_username"
    const val EXTRA_USER_DETAILS: String    = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE  = 2
    const val PICK_IMAGE_REQUEST_CODE       = 1

    const val GENDER: String    = "gender"
    const val MALE: String      = "ผู้ชาย"
    const val FEMALE: String    = "ผู้หญิง"
    const val LGBTQ: String     = "เพศทางเลือก"
    const val BIRTHDAY: String  = "birthDay"
    const val RESIDENT: String  = "residentType"
    const val HOME: String      = "บ้าน"
    const val APARTMENT: String = "หอพัก, อพาร์ตเมนท์ หรือ คอนโดมิเนียม"
    const val NONE: String      = "ยังไม่ได้ระบุ"


    const val MOBILE: String = "mobile"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE: String = "profileCompleted"
    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"

    //ข้อมูลผักหน้าค้นหา
    const val ves_name: String =""
    const val ves_details: String =""
    const val ves_details_size: String =""
    const val ves_details_how: String =""
    const val ves_details_water_sun: String =""
    const val ves_details_crop: String =""
    const val ves_details_time_crop: String =""
    const val ves_pic: String =""

    //ข้อมูลสินค้า
    const val goods_name: String =""
    const val goods_price: String =""
    const val goods_details: String =""
    const val goods_link: String =""

    //ข้อมูลผักที่User ปลูก
    const val USER_VES_NAME: String ="user_ves_name"
    const val USER_VES_TYPE: String ="user_ves_type"
    const val USER_VES_DATE: String ="user_ves_date"
    const val USER_VES_DES: String ="user_ves_des"

    fun plantType():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("กะเพรา")
        list.add("กว้างตุ้ง")
        list.add("ต้นหอม")
        list.add("ปวยเล้ง")
        list.add("ผักชี")
        list.add("ผักบุ้ง")
        list.add("ผักสลัด")
        list.add("พริก")
        list.add("มะกรูด")
        return list
    }

    //ข้อมูลการสร้าง To do list
    const val todolist_name: String =""
    const val todolist_des: String =""
    const val todolist_time: String =""
    const val task_type_name: String =""
    fun taskTypes():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("รดน้ำ")
        list.add("พรวนดิน")
        list.add("เติมปุ๋ย")
        list.add("กำจัดวัชพืช")
        list.add("เก็บเกี่ยว")

        return list
    }

    const val task_day_repeat: String =""
    fun dayRepeat():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("อาทิตย์")
        list.add("จันทร์")
        list.add("อังคาร")
        list.add("พุธ")
        list.add("พฤหัสบดี")
        list.add("ศุกร์")
        list.add("เสาร์")
        return list
    }


    fun showImageChoose(activity: Activity) {

        // An Intent for launching the image selection of image selection of phone storage.
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        // Launches the image selection of phone storage using the constat code.
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*

        Uri? = URI can be a no label
        MimeType: Two -way map that maps MIME-type to file extension and vice versa.

        getSingleton() : Get the singleton instance of MimeTypeMap

        getExtensionFromMimeType: Return the registered extension for the given MIME type

        contentResolver.getType: Return the MIME type of thr given content URL.
         */

        // uri = c:/user/milk/picture/home.jpg

        return  MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}