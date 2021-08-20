package com.kmitl.mythesis.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.kmitl.mythesis.models.Goods
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.models.User
import com.kmitl.mythesis.models.Vegetable
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.view.activities.*
import com.kmitl.mythesis.view.fragments.AllPlantFragment
import com.kmitl.mythesis.view.fragments.HomeFragment
import com.kmitl.mythesis.view.fragments.SearchFragment
import com.kmitl.mythesis.view.fragments.SearchVeganFragment

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

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
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

                        is AddPlantActivity -> {
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

                    is AddPlantActivity -> {
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

    fun uploadUserPlantData(activity: AddPlantActivity, plantInfo: Plant) {
        mFireStore.collection(Constants.USER_PLANT)
            .document()
            .set(plantInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it
                activity.userPlantUploadSuccess()
            }
            .addOnFailureListener { e ->

                activity.hideProgressDialog()

                Log.e(
                    activity.javaClass.simpleName,
                    "Oops! เกิดข้อผิดพลาดในการอัปโหลดข้อมูลผักของคุณ",
                    e
                )
            }
    }

    fun getPlantsList(fragment: Fragment) {
        mFireStore.collection(Constants.USER_PLANT)
            .whereEqualTo(Constants.USER_ID, getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.e("Plant List", document.documents.toString())
                val plantsList: ArrayList<Plant> = ArrayList()
                for (i in document.documents) {

                    val plant = i.toObject(Plant::class.java)
                    plant!!.plant_id = i.id

                    // for each plant
                    plantsList.add(plant)
                }

                when(fragment){
                    is HomeFragment  -> {
                        fragment.successPlantsListFromFirebase(plantsList)
                    }

                    is AllPlantFragment -> {
                        fragment.successPlantsListFromFirebase(plantsList)
                    }

                }
            }
    }

    fun getPlantDetails(activity: Activity, plant_id: String){
        mFireStore.collection(Constants.USER_PLANT)
            .document(plant_id)
            .get()
            .addOnSuccessListener { document ->
                Log.e(activity.javaClass.simpleName, document.toString())
                val plant = document.toObject(Plant::class.java)
                if (plant != null) {

                    when (activity) {
                        is PlantDetailsActivity -> {
                            activity.plantDetailsSuccess(plant)
                        }

                    }

                }
            }
            .addOnFailureListener {
                e ->
                when (activity) {
                    is PlantDetailsActivity -> {
                        activity.hideProgressDialog()
                    }

                }
                Log.e(activity.javaClass.simpleName, "Error while getting the plant details", e)
            }
    }

    fun deletePlant(fragment: AllPlantFragment, plant_id: String){
        mFireStore.collection(Constants.USER_PLANT)
            .document(plant_id)
            .delete()
            .addOnSuccessListener {

                fragment.plantDeleteSuccess()

            }.addOnFailureListener {
                e ->

                //Hide the progress dialog if there is an error.
                fragment.hideProgressDialog()

                Log.e(
                    fragment.requireActivity().javaClass.simpleName,
                    "Error while deleting the plant",
                    e
                )
            }
    }

    fun getVegansList(fragment: Fragment) {
        mFireStore.collection(Constants.INFO_VEGETABLES)
            .orderBy("ve_name")
            .get()
            .addOnSuccessListener { document ->
                Log.e("Vegetable List", document.documents.toString())
                val vegetablesList: ArrayList<Vegetable> = ArrayList()
                for (i in document.documents) {

                    val v = i.toObject(Vegetable::class.java)
                        v!!.ve_id = i.id

                    // for each plant
                    vegetablesList.add(v)
                }

                when(fragment){
                    is SearchFragment  -> {
                        fragment.successVeganListFromFirebase(vegetablesList)
                    }

                    is SearchVeganFragment -> {
                        fragment.successVeganListFromFirebase(vegetablesList)
                    }
                }

            }

    }

    fun getGoodsList(fragment: Fragment) {
        mFireStore.collection(Constants.INFO_GOODS)
            .orderBy("goods_name")
            .get()
            .addOnSuccessListener { document ->
                Log.e("Goods List", document.documents.toString())
                val goodsList: ArrayList<Goods> = ArrayList()
                for (i in document.documents) {

                    val g = i.toObject(Goods::class.java)
                    g!!.goods_id = i.id

                    // for each plant
                    goodsList.add(g)
                }

                when(fragment){
                    is SearchFragment  -> {
                        fragment.successGoodListFromFirebase(goodsList)
                    }
                }

            }

    }



}
