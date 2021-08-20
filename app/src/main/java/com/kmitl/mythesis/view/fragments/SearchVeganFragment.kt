package com.kmitl.mythesis.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.Vegetable
import com.kmitl.mythesis.view.adapters.VegetablesListAdapter
import kotlinx.android.synthetic.main.fragment_search.*

class SearchVeganFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root    = inflater.inflate(R.layout.fragment_search_vegan, container, false)

        val rd_all = root.findViewById<TextView>(R.id.rd_all)
        rd_all.setOnClickListener {
            showAllSearchFragment()
        }

        val rd_goods = root.findViewById<TextView>(R.id.rd_goods)
        rd_goods.setOnClickListener {
            showAllGoodsFragment()
        }

        return root
    }

    fun showAllSearchFragment() {
        val fragment: Fragment = SearchFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun showAllGoodsFragment() {
        val fragment: Fragment = SearchGoodsFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun successVeganListFromFirebase(vegansList: ArrayList<Vegetable>) {
        hideProgressDialog()

        rv_vegan_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        // rv_vegan_items.layoutManager = GridLayoutManager(activity, 4)
        rv_vegan_items.setHasFixedSize(true)
        val adapterVegans               = VegetablesListAdapter(requireActivity(), vegansList, this)
        rv_vegan_items.adapter          = adapterVegans
    }

    private fun getVeganListFromFirebase() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getVegansList(this)
    }

    override fun onResume() {
        super.onResume()
        getVeganListFromFirebase()
    }



}