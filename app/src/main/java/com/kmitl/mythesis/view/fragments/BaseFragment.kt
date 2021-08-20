package com.kmitl.mythesis.view.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmitl.mythesis.R
import kotlinx.android.synthetic.main.dialog_progress.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class BaseFragment : Fragment() {

    private lateinit var mProgressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

   fun showProgressDialog(text: String) {
       /* Set the screen content from a layout resource
       the resource will be inflated, adding all top-level views to the screen */
       mProgressDialog = Dialog(requireActivity())

       mProgressDialog.setContentView(R.layout.dialog_progress)

       mProgressDialog.label_pregress_text.text = text

       mProgressDialog.setCancelable(false)
       mProgressDialog.setCanceledOnTouchOutside(false)

       //start the dialog and display it on screen
       mProgressDialog.show()
   }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }
}