package com.kmitl.mythesis.view.activities

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kmitl.mythesis.R

open class BaseActivity : AppCompatActivity() {
    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    */

    private lateinit var mProgressDialog: Dialog

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if(errorMessage) {
             snackBarView.setBackgroundColor(
                 ContextCompat.getColor(
                     this@BaseActivity,
                     R.color.colorSnackBarError
                 )
             )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()

    }

    fun showProgressDialog(text: String) {
        mProgressDialog = Dialog(this)

        /* Set the screen content from a layout resource
        The resource will be inflated, adding all top-level view to the screen
        ตั้งข้อความจาก layout ข้อความอาจจะมีขนาดใหญ่ขึ้นนิดนึง */
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.findViewById<TextView>(R.id.label_pregress_text)

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        //เริ่ม dialog และ display บนจอ
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    open fun onClick(view: View?) {}
}