package com.kmitl.mythesis.view.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.view.activities.AddPlantActivity
import com.kmitl.mythesis.view.activities.AddTaskActivity
import com.kmitl.mythesis.viewmodel.HomeViewModel
import com.kmitl.mythesis.viewmodel.TaskViewModel

// To do list
class TaskFragment : Fragment() {
    private lateinit var taskViewModel: TaskViewModel

    companion object {
        fun newInstance() = TaskFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel =
            ViewModelProvider(this).get(TaskViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_task, container, false)

        val add_ic = root.findViewById<ImageView>(R.id.ic_add)
        add_ic.setOnClickListener {
            val intent=Intent(activity, AddTaskActivity::class.java)
            startActivity(intent)
        }

        return  root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        // TODO: Use the ViewModel
    }

}