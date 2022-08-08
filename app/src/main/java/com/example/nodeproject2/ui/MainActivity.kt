package com.example.nodeproject2.ui

import android.view.View
import androidx.fragment.app.Fragment
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding
import com.example.nodeproject2.ui.home.HomeFragment
import com.example.nodeproject2.ui.list.list.ListFragment
import com.example.nodeproject2.ui.search.SearchFragment
import com.example.nodeproject2.ui.timetable.TimetableFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var curFragment = "SearchFragment";
    var isSearchFragmentOn = false

    override fun init() {
        showFragment(HomeFragment(), "HomeFragment")
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.mainBtmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
                    showFragment(HomeFragment(), "HomeFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_subject -> {
                    showFragment(ListFragment(), "ListFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_timetable -> {
                    showFragment(TimetableFragment(), "TimetableFragment")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        binding.mainBtmNav.setOnItemReselectedListener { item ->
            if (item.itemId == R.id.menu_main_btm_nav_subject) {
                val listFragment = supportFragmentManager.findFragmentByTag("ListFragment") as ListFragment
                listFragment.changeTab()
            }

        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
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
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
        isSearchFragmentOn = true
//        showFragment(SearchFragment(), "SearchFragment")
    }

    fun changeToSubject() {
        binding.mainBtmNav.visibility = View.VISIBLE
        showFragment(ListFragment(), "ListFragment")
    }

    //Listener역할을 할 Interface 생성
    interface OnBackPressedListener {
        fun onBackPressed()
    }

    override fun onBackPressed() {
        if (isSearchFragmentOn) {
            val fragmentList = supportFragmentManager.fragments
            for (fragment in fragmentList) {
                if (fragment is OnBackPressedListener) {
                    (fragment as OnBackPressedListener).onBackPressed()
                    isSearchFragmentOn = false
                    return
                }
            }
        } else {
            super.onBackPressed()
        }
    }
}



