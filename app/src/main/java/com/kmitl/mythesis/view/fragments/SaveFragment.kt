package com.kmitl.mythesis.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.view.activities.GameMyPlantActivity
import com.kmitl.mythesis.viewmodel.HomeViewModel
import com.kmitl.mythesis.viewmodel.SaveViewModel

class SaveFragment : Fragment() {
    private lateinit var saveViewModel: SaveViewModel
    private lateinit var viewModel: SaveViewModel
    private lateinit var btn_back : ImageView

    companion object {
        fun newInstance() = SaveFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        saveViewModel =
            ViewModelProvider(this).get(SaveViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_save, container, false)

        val btn_back = root.findViewById<TextView>(R.id.ic_back)
        btn_back.setOnClickListener {
            val intent=Intent(activity, ProfileFragment::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SaveViewModel::class.java)
        // TODO: Use the ViewModel
    }

}



