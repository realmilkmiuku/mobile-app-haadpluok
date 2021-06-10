package com.kmitl.mythesis.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kmitl.mythesis.view.activities.LoginActivity
import com.kmitl.mythesis.view.activities.RegisterActivity
import com.kmitl.mythesis.view.activities.UserProfileActivity
import com.kmitl.mythesis.models.User
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.view.activities.AddPlantActivity

class FirestoreClass {

    private  val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        // for fireStore and My program
        // users  = collection name สร้างชื่อซ้ำไม่ได้
        mFireStore.collection(Constants.USERS)
            //doc ID for user field. Here the doc it is the user id or uid
            .document(userInfo.id)
            //userInfo field and setOption is set to merge. It is for if we want to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "เกิดข้อผิดพลาดในการลงทะเบียน",
                    e
                )
            }
    }

    fun getCurrentUserID(): String {
        //ผู้ใช้ปัจจุบัน
        val currentUser = FirebaseAuth.getInstance().currentUser

        //ตัวแปรเพื่อกำหนด userID ปัจจุบัน หากไม่ใช่ค่าว่าง = มีค่าจะกำหนด uid ปัจจุบันในตัวแปร currentUserID
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID

    }

    fun getUserDetails(activity: Activity) {

        //ส่งชื่อคอลเลกชันที่เราต้องการข้อมูล
        //inside collection have doc
        mFireStore.collection(Constants.USERS)

            //inside doc have fields
            //doc id to get fields from the doc of users
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                //ได้รับสแน็ปช็อตเอกสารซึ่งถูกแปลงเป็น obj โมเดลข้อมูลผู้ใช้

                val user = document.toObject(User::class.java)!!

                val sharedPreferences =
                    activity.getSharedPreferences(
                        Constants.MYTEHSIS_PREFERENCE,
                        Context.MODE_PRIVATE
                    )

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                //Key : logged in username - Test1 milkkywayyy@gmail.com
                editor.putString(
                    Constants.LOGGEN_IN_USERNAME,
                    "${user.userName} ${user.email}"
                )
                editor.apply()

                // TODO : Pass the result to the Login Activity.
                //Start
                when (activity) {
                    is LoginActivity -> {
                        //Call a function of base activity for transferring the result to it
                        activity.userLoggedInSuccess(user)
                    }
                }

                //End
            }

            .addOnFailureListener { e ->
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()

                    }
                }

                Log.e(
                        activity.javaClass.simpleName,
                        "เกิดข้อผิดพลาดในการเข้าสู่ระบบ",
                        e
                )
            }
    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {

       mFireStore.collection(Constants.USERS)
           .document(getCurrentUserID())
           .update(userHashMap)
           .addOnSuccessListener {

               when (activity) {
                   is UserProfileActivity -> {
                       activity.userProfileUpdateSuccess()
                   }
               }
           }
           .addOnFailureListener { e->
               when (activity) {
                   is UserProfileActivity -> {
                       activity.hideProgressDialog()
                   }
               }

               Log.e(
                   activity.javaClass.simpleName,
                   "การอัปเดตข้อมูลผิดพลาด",
                   e
               )
           }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "."
            + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { taskSnapshot ->
            //The image upload is success
            Log.e(
                "Firebase Image URI",
               taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            // Get the downloadable url from the task snapshot
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri ->
                    Log.e("Downloadable Image URI", uri.toString())
                    when (activity) {
                        is UserProfileActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }
        }

            .addOnFailureListener { exception ->
                //Hide the progress dialog if there is any error.And print the error in log.
                when(activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun createUserPlantData(activity: Activity, userHashMap: HashMap<String, Any>) {

    }


}
