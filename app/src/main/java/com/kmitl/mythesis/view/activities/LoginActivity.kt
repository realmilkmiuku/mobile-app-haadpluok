package com.kmitl.mythesis.view.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.User
import com.kmitl.mythesis.utils.Constants

class LoginActivity : BaseActivity(), View.OnClickListener {
    private lateinit var et_email       : EditText
    private lateinit var et_password    : EditText
    private lateinit var btn_login      : Button
    private lateinit var link_forgetPassword : TextView
    private lateinit var link_regis     : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_email            = findViewById(R.id.et_email)
        et_password         = findViewById(R.id.et_num)
        btn_login           = findViewById(R.id.btn_login )
        link_forgetPassword = findViewById(R.id.hyperlink_forgetPassword)
        link_regis          = findViewById(R.id.hyperlink_regis)

        // คลิกปุ่มลืมรหัสผ่าน
        link_forgetPassword.setOnClickListener(this)

        //คลิกปุ่มล็อกอิน
        btn_login.setOnClickListener(this)

        //คลิกลงทะเบียน
        link_regis.setOnClickListener(this)

    }

    fun userLoggedInSuccess(user: User) {
        hideProgressDialog()
        Toast.makeText(
            this@LoginActivity,
            resources.getString(R.string.login_successful),
            Toast.LENGTH_SHORT
        ).show()

        if (user.profileCompleted == 0) {
            // ถ้าโปรไฟล์ยังไม่สมบูรณ์ แสดงหน้าโปรไฟล์
            val intent = Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        } else {
            // redirect the user to main screen after login
            startActivity(Intent(this@LoginActivity, BottomNavActivity::class.java))

        }
        finish()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.hyperlink_forgetPassword -> {
                    val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
                    startActivity(intent)
                }

                R.id.btn_login -> {
                    //Call the validate func
                    //Start
                    loginUser()
                    //End
                }

                R.id.hyperlink_regis -> {
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun validateLoginDetails(): Boolean {
        return when{
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_username), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            else -> {
                true
            }
        }
    }

    private fun loginUser() {

        //Check with validate func if the entries are valid or not
        if (validateLoginDetails()) {

            showProgressDialog(resources.getString(R.string.please_wait))

            //ตรวจสอบว่ามีข้อมูลใน Input
            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            // Log -In using FirebaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener  { task ->

                        // ถ้าเข้าสู่ระบบได้แล้ว
                        if (task.isSuccessful) {
                            // TODO - Send user to Main Activity
                            FirestoreClass().getUserDetails(this@LoginActivity)
                        } else {
                            // เข้าสู่ระบบไม่ได้ ให้แสดงข้อความ
                            hideProgressDialog()
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }

                    }
        }
    }

}