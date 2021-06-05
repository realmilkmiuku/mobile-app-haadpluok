package com.kmitl.mythesis.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.kmitl.mythesis.R

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var ic_back       : ImageView
    private lateinit var toolbar_register_activity : Toolbar
    private lateinit var link_regis     : TextView
    private lateinit var et_email       : EditText
    private lateinit var btn_summit     : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        ic_back             = findViewById(R.id.ic_back)
        link_regis          = findViewById(R.id.hyperlink_regis)
        et_email            = findViewById(R.id.et_email  )
        btn_summit          = findViewById(R.id.btn_summit )

        ic_back.setOnClickListener {
             onBackPressed()
        }

        summitForgetPassword()

        link_regis.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupActionBar() {

        setSupportActionBar(findViewById(R.id.toolbar_register_activity))

        val actionBar = supportActionBar
        if (  actionBar != null) {
            this.actionBar!!.setDisplayHomeAsUpEnabled(true)
            this.actionBar!!.setHomeAsUpIndicator(R.drawable.ic_back)
        }

        toolbar_register_activity.setNavigationOnClickListener { onBackPressed() }

    }

    private fun summitForgetPassword() {
        btn_summit.setOnClickListener {
            val email: String = et_email.text.toString().trim { it <= ' ' }
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener{task ->
                        hideProgressDialog()
                        if(task.isSuccessful) {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                getString(R.string.email_sent_success),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(),true)
                        }
                    }
            }
        }
    }
}