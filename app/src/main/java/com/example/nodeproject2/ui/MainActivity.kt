package com.example.nodeproject2.ui

import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import androidx.transition.Visibility
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding
import com.example.nodeproject2.ui.home.HomeFragment
import com.example.nodeproject2.ui.search.SearchFragment
import com.example.nodeproject2.ui.subject.ElectiveFragment
import com.example.nodeproject2.ui.subject.SubjectFragment
import com.example.nodeproject2.ui.timetable.TimeTableViewModel
import com.example.nodeproject2.ui.timetable.TimetableFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var isFabOpen = false

    override fun init() {
//        fabOff()

        showFragment(HomeFragment(), "HomeFragment")

        binding.mainBtmNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_main_btm_nav_home -> {
//                    fabOff()
                    showFragment(HomeFragment(), "HomeFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_subject -> {
//                    fabOn()
                    showFragment(SubjectFragment(), "SubjectFragment")
                    return@setOnItemSelectedListener true
                }
                R.id.menu_main_btm_nav_timetable -> {
//                    fabOff()
                    showFragment(TimetableFragment(), "TimetableFragment")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

//        initFab()
    }
//
//    private fun initFab() {
//        binding.apply {
//            fabMain.setOnClickListener {
//                toggleFab()
//            }
//            fabMajor.setOnClickListener {
//                showFragment(SubjectFragment(), "SubjectFragment")
//                closeFab()
//            }
//            fabElective.setOnClickListener {
//                showFragment(ElectiveFragment(), "ElectiveFragment")
//                closeFab()
//            }
//        }
//    }

//    private fun toggleFab() {
//        binding.apply {
//            if (isFabOpen) {
//                closeFab()
//            } else {
//                openFab()
//            }
//        }
//    }

//    private fun closeFab() {
//        binding.apply {
//            ObjectAnimator.ofFloat(fabMajor, "translationY", 0f).apply { start() }
//            ObjectAnimator.ofFloat(fabElective, "translationY", 0f).apply { start() }
//            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 45f, 0f).apply { start() }
//        }
//        isFabOpen = !isFabOpen
//    }

//    private fun openFab() {
//        binding.apply {
//            ObjectAnimator.ofFloat(fabMajor, "translationY", -360f).apply { start() }
//            ObjectAnimator.ofFloat(fabElective, "translationY", -180f).apply { start() }
//            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 0f, 45f).apply { start() }
//        }
//        isFabOpen = !isFabOpen
//    }
//
//    private fun fabOn() {
//        binding.fabMain.visibility = View.VISIBLE
//        binding.fabMajor.visibility = View.VISIBLE
//        binding.fabElective.visibility = View.VISIBLE
//    }
//
//    private fun fabOff() {
//        binding.fabMain.visibility = View.GONE
//        binding.fabMajor.visibility = View.GONE
//        binding.fabElective.visibility = View.GONE
//    }

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

    fun changeToSearch() {
        binding.mainBtmNav.visibility = View.GONE
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        binding.fragmentContainer.layoutParams = params
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, SearchFragment()).commit()
//        showFragment(SearchFragment(), "SearchFragment")
    }



    fun changeToSubject() {
//        softkeyboardHide()

        binding.mainBtmNav.visibility = View.VISIBLE
        val params = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        val dm = resources.displayMetrics
        val size = (60 * dm.density).roundToInt()
        params.bottomMargin = size
        binding.fragmentContainer.layoutParams = params
        showFragment(SubjectFragment(), "SubjectFragment")
    }
//
//    fun softkeyboardHide() {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//    }



}