package com.kmitl.mythesis.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.kmitl.mythesis.R


class SearchGoodsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root    = inflater.inflate(R.layout.fragment_search_goods, container, false)

        val rd_all = root.findViewById<TextView>(R.id.rd_all)
        rd_all.setOnClickListener {
            showAlLSearchFragment()
        }

        val rd_ve = root.findViewById<TextView>(R.id.rd_ve)
        rd_ve.setOnClickListener {
            showAllVeganFragment()
        }

        return root
    }

    fun showAlLSearchFragment() {
        val fragment: Fragment = SearchFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    fun showAllVeganFragment() {
        val fragment: Fragment = SearchVeganFragment()
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}