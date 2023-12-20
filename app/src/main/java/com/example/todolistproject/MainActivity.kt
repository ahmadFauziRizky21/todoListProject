package com.example.todolistproject

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.todolistproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private val fragHome : Fragment = DashboardFragment()
    private val fragProfile : Fragment = ProfileFragment()
    private val fm : FragmentManager = supportFragmentManager
    private var active : Fragment = fragHome

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var menu : Menu
    private lateinit var menuItem: MenuItem

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavBottom()
    }



    private fun setUpNavBottom() {

        bottomNavigationView = binding.navView
        menu = bottomNavigationView.menu
        menuItem = menu.getItem(0)
        menuItem.isChecked = true

        fm.beginTransaction().add(R.id.nav_home,fragHome).show(fragHome).commit()
        fm.beginTransaction().add(R.id.nav_profile,fragProfile).hide(fragProfile).commit()
        bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    callFragment(0, fragHome)
                }
                R.id.nav_profile -> {
                    callFragment(1, fragProfile)
                }
            }
        }
    }
    private fun callFragment(index : Int , fragment: Fragment) {
        menuItem = menu.getItem(index)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }


}