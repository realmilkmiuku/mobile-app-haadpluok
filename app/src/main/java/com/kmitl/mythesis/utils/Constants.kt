package com.kmitl.mythesis.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.google.type.Date

object Constants {
    const val USERS: String = "users"
    const val MYTEHSIS_PREFERENCE: String   = "MyThesisPrefs"
    const val LOGGEN_IN_USERNAME: String    = "logged_in_username"
    const val EXTRA_USER_DETAILS: String    = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE  = 2
    const val PICK_IMAGE_REQUEST_CODE       = 1

    const val MALE: String = "ผู้ชาย"
    const val FEMALE: String = "ผู้หญิง"
    const val LGBTQ: String = "LGBTQ+"

    const val BIRTHDAY: String = "birthday"

    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
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
    const val user_ves_name: String =""
    const val user_ves_date: String =""
    const val user_ves_des: String =""

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