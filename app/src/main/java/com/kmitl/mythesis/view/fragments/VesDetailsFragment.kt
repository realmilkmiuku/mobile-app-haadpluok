package com.kmitl.mythesis.view.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kmitl.mythesis.R
import com.kmitl.mythesis.viewmodel.VesDetailsViewModel

class VesDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = VesDetailsFragment()
    }

    private lateinit var viewModel: VesDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ves_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VesDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}