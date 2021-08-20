package com.kmitl.mythesis.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.view.activities.AddPlantActivity
import com.kmitl.mythesis.view.adapters.MyPlantListCircleAdapter
import com.kmitl.mythesis.view.adapters.MyPlantListRectangleAdapter
import com.kmitl.mythesis.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class AllPlantFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root    = inflater.inflate(R.layout.fragment_all_plant, container, false)

        val backIc = root.findViewById<ImageView>(R.id.ic_back)
        backIc.setOnClickListener {
            val fragment: Fragment = HomeFragment()
            val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frameLayout, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val addIc = root.findViewById<ImageView>(R.id.ic_add)
        addIc.setOnClickListener {
            val intent=Intent(activity,AddPlantActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    fun deletePlant(plant_id: String) {
        showAlertDialogToDelete(plant_id)
    }

    private fun showAlertDialogToDelete(plant_id: String) {
        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.delete_dialog_title))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.delete_dialog_msg))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _->
            showProgressDialog(resources.getString(R.string.please_wait))

            FirestoreClass().deletePlant(this, plant_id)

            dialogInterface.dismiss()
        }

        //performing negative action
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _->

            dialogInterface.dismiss()
        }

        //Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        //Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun plantDeleteSuccess() {
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.msg_plant_delete_success),
            Toast.LENGTH_SHORT
        ).show()

        getPlantListFromFirebase()
    }

    fun successPlantsListFromFirebase(plantsList: ArrayList<Plant>) {
        hideProgressDialog()

        if (plantsList.size > 0 ) {
            rv_my_plant_items.visibility    = View.VISIBLE
            tv_no_plant.visibility          = View.GONE

            //rv_my_plant_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            rv_my_plant_items.layoutManager = GridLayoutManager(activity, 2)
            rv_my_plant_items.setHasFixedSize(true)
            val adapterPlants               = MyPlantListRectangleAdapter(requireActivity(), plantsList, this)
            rv_my_plant_items.adapter       = adapterPlants
        }else{
            rv_my_plant_items.visibility    = View.GONE
            tv_no_plant.visibility          = View.VISIBLE
        }

    }

    private fun getPlantListFromFirebase() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getPlantsList(this)
    }

    override fun onResume() {
        super.onResume()
        getPlantListFromFirebase()
    }

}