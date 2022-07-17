package com.example.nodeproject2.ui

import android.os.Bundle
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding
import com.example.nodeproject2.ui.home.HomeFragment
import com.example.nodeproject2.ui.subject.SubjectFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_subject -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, SubjectFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_timetable -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frame, com.example.nodeproject2.ui.subject.SubjectFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

}