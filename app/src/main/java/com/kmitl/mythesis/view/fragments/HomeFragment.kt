package com.kmitl.mythesis.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.Plant
import com.kmitl.mythesis.view.activities.AddPlantActivity
import com.kmitl.mythesis.view.activities.GameMyPlantActivity
import com.kmitl.mythesis.view.adapters.MyPlantListCircleAdapter
import com.kmitl.mythesis.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*val textView = root.findViewById<TextView>(R.id.user_province)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/

        val tvMyPlant = root.findViewById<ImageView>(R.id.tv_myplant)
        tvMyPlant.setOnClickListener {
            val intent=Intent(activity, GameMyPlantActivity::class.java)
            startActivity(intent)
        }

        val addIc = root.findViewById<ImageView>(R.id.ic_add)
        addIc.setOnClickListener {
            val intent=Intent(activity,AddPlantActivity::class.java)
           startActivity(intent)
        }

        val tvAllPlant = root.findViewById<TextView>(R.id.link_all_plant)
        tvAllPlant.setOnClickListener {
            showAllPlantFragment()
        }

        val cv_search_vegan = root.findViewById<CardView>(R.id.cv_search_vegan)
        cv_search_vegan.setOnClickListener {
            showSearchVeganFragment()
        }

        val cv_search_goods = root.findViewById<CardView>(R.id.cv_search_goods)
        cv_search_goods.setOnClickListener {
            showSearchGoodsFragment()
        }

        return root
    }

    fun showAllPlantFragment() {
        val fragment: Fragment = AllPlantFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun showSearchVeganFragment() {
        val fragment: Fragment = SearchVeganFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun showSearchGoodsFragment() {
        val fragment: Fragment = SearchGoodsFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun successPlantsListFromFirebase(plantsList: ArrayList<Plant>) {
        hideProgressDialog()

        if (plantsList.size > 0 ) {
            link_all_plant.visibility    = View.VISIBLE
            rv_my_plant_items.visibility    = View.VISIBLE
            tv_no_plant.visibility          = View.GONE

            rv_my_plant_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            //rv_my_plant_items.layoutManager = GridLayoutManager(activity, 4)
            rv_my_plant_items.setHasFixedSize(true)
            val adapterPlants               = MyPlantListCircleAdapter(requireActivity(), plantsList, this)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun gridView() {

        // Set the LayoutManager that this RecyclerView will use.
        //rv_my_plant_items.layoutManager = GridLayoutManager(activity, 2)

    }


}