package com.example.nodeproject2.ui

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding
import com.example.nodeproject2.ui.home.HomeFragment
import com.example.nodeproject2.ui.search.SearchFragment
import com.example.nodeproject2.ui.subject.ElectiveFragment
import com.example.nodeproject2.ui.subject.SubjectFragment
import com.example.nodeproject2.ui.timetable.TimetableFragment
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var curFragment = "SearchFragment";

    override fun init() {
        showFragment(HomeFragment(), "HomeFragment")
        initBottomNavigation()
        initTab()
    }

    private fun initBottomNavigation() {
        binding.mainBtmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
                    showFragment(HomeFragment(), "HomeFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_subject -> {
                    showFragment(SubjectFragment(), "SubjectFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_timetable -> {
                    showFragment(TimetableFragment(), "TimetableFragment")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    private fun initTab() {
        binding.apply {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when (tab!!.position) {
                        0 -> showFragment(SubjectFragment(), "SubjectFragment")
                        1 -> showFragment(ElectiveFragment(), "ElectiveFragment")
                    }
                }
            })
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        when (tag) {
            "HomeFragment" -> {
//                supportActionBar!!.show()
                binding.tabLayout.visibility = View.GONE
            }
            "SubjectFragment" -> {
//                supportActionBar!!.hide()
                binding.tabLayout.visibility = View.VISIBLE
            }
            "TimetableFragment" -> {
//                supportActionBar!!.show()
                binding.tabLayout.visibility = View.GONE
            }
        }
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        supportFragmentManager.fragments.forEach { fm ->
            fragmentTransaction.hide(fm)
        }

        findFragment?.let { fm ->
            fragmentTransaction.show(fm).commit()
        } ?: kotlin.run {
            fragmentTransaction
                .add(R.id.fragment_container, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    fun changeToSearch(fragment: String) {
        binding.mainBtmNav.visibility = View.GONE
        curFragment = fragment
//        supportFragmentManager.beginTransaction().addToBackStack("SubjectFragment")
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
//        showFragment(SearchFragment(), "SearchFragment")
    }

    fun changeToSubject() {
        binding.mainBtmNav.visibility = View.VISIBLE

        if (curFragment == "SubjectFragment") {
            showFragment(SubjectFragment(), "SubjectFragment")
        }else {
            showFragment(ElectiveFragment(), "ElectiveFragment")
        }

    }

//    fun changeToElective() {
//        binding.mainBtmNav.visibility = View.VISIBLE
//        showFragment(ElectiveFragment(), "ElectiveFragment")
//    }
//
//    override fun onNewIntent(intent: Intent?) {
//        super.onNewIntent(intent)
//        val startId = intent?.getIntExtra("startId", R.id.fragment_home_layout) ?: R.id.fragment_home_layout
//
//        println("====================================")
//        showFragment(HomeFragment(), "HomeFragment")
//
//    }

}