package com.example.nodeproject2.ui

import android.animation.ObjectAnimator
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding
import com.example.nodeproject2.ui.home.HomeFragment
import com.example.nodeproject2.ui.subject.ElectiveFragment
import com.example.nodeproject2.ui.subject.SubjectFragment
import com.example.nodeproject2.ui.timetable.TimeTableViewModel
import com.example.nodeproject2.ui.timetable.TimetableFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var isFabOpen = false

    override fun init() {
        fabOff()

        showFragment(HomeFragment(), "HomeFragment")

        binding.mainBtmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
                    fabOff()
                    showFragment(HomeFragment(), "HomeFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_subject -> {
                    fabOn()
                    showFragment(SubjectFragment(), "SubjectFragment")
//                    SubjectFragment().binding.viewModel.()
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_timetable -> {
                    fabOff()
                    showFragment(TimetableFragment(), "TimetableFragment")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        initFab()
    }

    private fun initFab() {
        binding.apply {
            fabMain.setOnClickListener {
                toggleFab()
            }
            fabMajor.setOnClickListener {
                showFragment(SubjectFragment(), "SubjectFragment")
                closeFab()
            }
            fabElective.setOnClickListener {
                showFragment(ElectiveFragment(), "ElectiveFragment")
                closeFab()
            }
        }
    }

    private fun toggleFab() {
        binding.apply {
            if (isFabOpen) {
                closeFab()
            } else {
                openFab()
            }
        }
    }

    private fun closeFab() {
        binding.apply {
            ObjectAnimator.ofFloat(fabMajor, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(fabElective, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 45f, 0f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }

    private fun openFab() {
        binding.apply {
            ObjectAnimator.ofFloat(fabMajor, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(fabElective, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }

    private fun fabOn() {
        binding.fabMain.visibility = View.VISIBLE
        binding.fabMajor.visibility = View.VISIBLE
        binding.fabElective.visibility = View.VISIBLE
    }

    private fun fabOff() {
        binding.fabMain.visibility = View.GONE
        binding.fabMajor.visibility = View.GONE
        binding.fabElective.visibility = View.GONE
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

}