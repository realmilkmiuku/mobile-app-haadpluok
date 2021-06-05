package com.kmitl.mythesis.view.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kmitl.mythesis.R
import com.kmitl.mythesis.utils.Constants
import com.kmitl.mythesis.view.fragments.HomeFragment
import com.kmitl.mythesis.view.fragments.ProfileFragment
import com.kmitl.mythesis.view.fragments.SearchFragment
import com.kmitl.mythesis.view.fragments.TaskFragment

class BottomNavActivity : AppCompatActivity() {
    private lateinit var tv_main : TextView
    lateinit var homeFragment: HomeFragment
    lateinit var todolistFragment: TaskFragment
    lateinit var searchFragment: SearchFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        var bottomnav : BottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        var frameLayout : FrameLayout = findViewById(R.id.frameLayout)

        tv_main = findViewById<TextView>(R.id.tv_main)

        val sharedPreferences = getSharedPreferences(Constants.MYTEHSIS_PREFERENCE, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGEN_IN_USERNAME, " ")!!
        tv_main.text = "Hello $username."

        homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, homeFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomnav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, homeFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.todolist -> {
                    todolistFragment = TaskFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, todolistFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.search -> {
                    searchFragment = SearchFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, searchFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.profile -> {
                    profileFragment = ProfileFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frameLayout, profileFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true
        }
    }
}