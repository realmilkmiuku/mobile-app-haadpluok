package com.kmitl.mythesis.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import com.kmitl.mythesis.R
import com.kmitl.mythesis.view.activities.AddPlantActivity
import com.kmitl.mythesis.view.activities.GameMyPlantActivity
import com.kmitl.mythesis.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView = root.findViewById<TextView>(R.id.user_province)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val tv_myplant = root.findViewById<TextView>(R.id.tv_myplant)
        tv_myplant.setOnClickListener {
            val intent=Intent(activity, GameMyPlantActivity::class.java)
            startActivity(intent)
        }

        val add_ic = root.findViewById<ImageView>(R.id.ic_add)
        add_ic.setOnClickListener {
            val intent=Intent(activity,AddPlantActivity::class.java)
           startActivity(intent)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_plant, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_add_plant -> {
                startActivity(Intent(requireActivity(), AddPlantActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}