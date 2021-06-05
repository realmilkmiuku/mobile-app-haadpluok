package com.kmitl.mythesis.view.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.User


class RegisterActivity : BaseActivity() {
    private lateinit var link_login : TextView
    private lateinit var et_username    : EditText
    private lateinit var et_email       : EditText
    private lateinit var et_password    : EditText
    private lateinit var et_confirm_password    : EditText
    private lateinit var et_cb_terms_and_condition : AppCompatCheckBox
    private lateinit var btn_regis  : Button
    private lateinit var toolbar_register_activity : Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        link_login  = findViewById(R.id.hyperlink_login)
        btn_regis   = findViewById(R.id.btn_regis)
        et_username = findViewById(R.id.et_username)
        et_email    = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_num)
        et_confirm_password             = findViewById(R.id.et_confirm_password)
        et_cb_terms_and_condition       = findViewById(R.id.et_cb_terms_and_condition)


        link_login.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        
        btn_regis.setOnClickListener {
            registerUser()
        }


    }

    private fun validateRegisterDetails(): Boolean {
        return when{
            TextUtils.isEmpty(et_username.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_username), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            !et_cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_cd), true)
                false
            }

            else -> {
                //showErrorSnackBar(resources.getString(R.string.register_successful), false)
                true
            }
        }
    }

    private fun registerUser() {

        //Check with validate func if the entries are valid or not
        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            //ตรวจสอบว่ามีข้อมูลใน Input
            val email: String = et_email.text.toString().trim() { it <= ' ' }
            val password: String = et_password.text.toString().trim() { it <= ' ' }

            //Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        // ถ้าลงทะเบียนได้แล้ว
                        if (task.isSuccessful) {

                            //Firebase เพิ่มข้อมูลลงทะเบียนของ user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                et_username.text.toString().trim { it <= ' ' },
                                et_email.text.toString().trim { it <= ' ' }
                            )

                            //Storage in cloud
                            FirestoreClass().registerUser(this@RegisterActivity, user)
                            FirebaseAuth.getInstance().signOut()
                            userRegistrationSuccess()
                            finish()

                        } else {
                            hideProgressDialog()
                            // ลงทะเบียนไม่ได้ ให้แสดงข้อความ
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    fun userRegistrationSuccess() {

        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,
            resources.getString(R.string.register_successful),
            Toast.LENGTH_SHORT
        ).show()

    }



}