package com.kmitl.mythesis.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kmitl.mythesis.R
import com.kmitl.mythesis.firestore.FirestoreClass
import com.kmitl.mythesis.models.INFO_GENERATE_TASK
import com.kmitl.mythesis.models.INFO_USER_TASK
import com.kmitl.mythesis.view.activities.AddTaskPickUplantActivity
import com.kmitl.mythesis.view.activities.SplashActivity
import com.kmitl.mythesis.view.adapters.GTaskListAdapter
import com.kmitl.mythesis.view.adapters.UTaskListAdapter
import com.kmitl.mythesis.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.Calendar


// To do list
class TaskFragment : BaseFragment() {
    var gotLatt: String = SplashActivity.getLatt
    var gotLonn: String = SplashActivity.getLonn
    val gotAPI: String = SplashActivity.getAPI
    
    private lateinit var taskViewModel: TaskViewModel

    private var textDayno: String = ""
    private var textMonth: String = ""
    private var textYear: String = ""

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
            val intent=Intent(activity, AddTaskPickUplantActivity::class.java)
            startActivity(intent)
        }



        return  root
    }

    fun successUTasksListFromFirebase(utaskList: ArrayList<INFO_USER_TASK>) {
        hideProgressDialog()

        if (utaskList.size > 0 ) {
            tv_no_utask.visibility    = View.GONE
            rv_utask_items.visibility    = View.VISIBLE
            rv_utask_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rv_utask_items.setHasFixedSize(true)
            val adapterUTask             = UTaskListAdapter(requireActivity(), utaskList)
            rv_utask_items.adapter       = adapterUTask

        }else{
            rv_utask_items.visibility    = View.GONE
            tv_no_utask.visibility       = View.VISIBLE
        }

    }

    private fun getUTaskListFromFirebase() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getUTasksList(this)
    }

    fun successGTasksListFromFirebase(utaskList: ArrayList<INFO_GENERATE_TASK>) {
        hideProgressDialog()

        if (utaskList.size > 0 ) {
            tv_no_utask.visibility    = View.GONE
            rv_gtask_items.visibility    = View.VISIBLE
            rv_gtask_items.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            rv_gtask_items.setHasFixedSize(true)
            val adapterTask             = GTaskListAdapter(requireActivity(), utaskList)
            rv_gtask_items.adapter       = adapterTask

        }else{
            rv_gtask_items.visibility    = View.GONE
            tv_no_utask.visibility       = View.VISIBLE
        }

        getUTaskListFromFirebase()

    }

    private fun getGTaskListFromFirebase() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getGTasksList(this)
    }

    override fun onResume() {
        super.onResume()
        pickDay()
        tv_dayno.text = textDayno
        tv_month.text = textMonth
        tv_year.text = textYear
        getGTaskListFromFirebase()
    }

    private fun pickDay() {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val strMonths = arrayOf(
            "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม",
            "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม" ,"พฤศจิกายน", "ธันวาคม"
        )

        val month_name = strMonths[month]

        textMonth = month_name + " "
        textYear = year.toString() + " "
        textDayno = day.toString() + " "
    }

}