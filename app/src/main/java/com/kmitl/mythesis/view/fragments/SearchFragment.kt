package com.kmitl.mythesis.view.fragments


import androidx.lifecycle.ViewModelProvider
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
import com.kmitl.mythesis.models.Goods
import com.kmitl.mythesis.models.Vegetable
import com.kmitl.mythesis.view.adapters.GoodsListAdapter
import com.kmitl.mythesis.view.adapters.VegetablesListAdapter
import com.kmitl.mythesis.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : BaseFragment() {

    private lateinit var viewModel: SearchViewModel

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root    = inflater.inflate(R.layout.fragment_search, container, false)

        val tv_see_all_ves = root.findViewById<TextView>(R.id.tv_see_all_ves)
        tv_see_all_ves.setOnClickListener {
            showAllVeganFragment()
        }

        val tv_see_all_goods = root.findViewById<TextView>(R.id.tv_see_all_goods)
        tv_see_all_goods.setOnClickListener {
            showAllGoodsFragment()
        }

        val rd_ve = root.findViewById<TextView>(R.id.rd_ve)
        rd_ve.setOnClickListener {
            showAllVeganFragment()
        }

        val rd_goods = root.findViewById<TextView>(R.id.rd_goods)
        rd_goods.setOnClickListener {
            showAllGoodsFragment()
        }

        //myLinearLayout.setClickable(true);
        return root
    }

    fun showAllVeganFragment() {
        val fragment: Fragment = SearchVeganFragment()
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
        //rv_my_plant_items.layoutManager = GridLayoutManager(activity, 4)
        rv_vegan_items.setHasFixedSize(true)
        val adapterVegans               = VegetablesListAdapter(requireActivity(), vegansList, this)
        rv_vegan_items.adapter          = adapterVegans
    }

    private fun getVeganListFromFirebase() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getVegansList(this)
    }

    private fun getGoodsListFromFirebase() {
        //showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getGoodsList(this)
    }

    fun successGoodListFromFirebase(goodsList: ArrayList<Goods>) {
        hideProgressDialog()
        rv_goods_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        //rv_my_plant_items.layoutManager = GridLayoutManager(activity, 4)
        rv_goods_items.setHasFixedSize(true)
        val adapterGoods                = GoodsListAdapter(requireActivity(), goodsList, this)
        rv_goods_items.adapter          = adapterGoods
    }


    override fun onResume() {
        super.onResume()
        getVeganListFromFirebase()
        getGoodsListFromFirebase()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}