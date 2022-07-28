package com.example.nodeproject2.ui.subject

import android.animation.ObjectAnimator
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentSubjectBinding
import com.example.nodeproject2.ui.subject.adapter.SubjectAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubjectFragment : BaseFragment<FragmentSubjectBinding>(R.layout.fragment_subject) {

    private val viewModel by viewModels<SubjectViewModel>()
    private lateinit var subjectAdapter: SubjectAdapter

    private var isFabOpen = false

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        viewModel.getInitData()

        initFab()
//        initMajorTextView()
        initRecyclerView()
        observeRecyclerView()
    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitList(it)
            hideLoadingDialog()
        }

        viewModel.subjectErrorToastEvent.observe(viewLifecycleOwner) {
            showCustomToast("실패 실패 실패 실패")
            hideLoadingDialog()
        }

        viewModel.subjectWaitEvent.observe(viewLifecycleOwner) {
            showLoadingDialog()
        }

    }

    private fun initRecyclerView() {

        subjectAdapter = SubjectAdapter(viewModel)
        binding.rvSubject.adapter = subjectAdapter

    }

//    private fun initMajorTextView() {
//        val departmentArray = resources.getStringArray(R.array.department_list)
//
//        val adapter = ArrayAdapter<String>(
//            activity!!.applicationContext,
//            android.R.layout.simple_dropdown_item_1line,
//            departmentArray
//        )
//
//        val majorTextView = binding.majorAutoCompleteTv
//
//        majorTextView.setAdapter(adapter)
//
//    }

    private fun initFab() {
        binding.apply {
            fabMain.setOnClickListener {
                toggleFab()
            }
            fabMajor.setOnClickListener {
                Toast.makeText(activity!!.applicationContext, "캡처 버튼 클릭!", Toast.LENGTH_SHORT).show()
                closeFab()
            }
            fabElectives.setOnClickListener {
                Toast.makeText(activity!!.applicationContext, "공유 버튼 클릭!", Toast.LENGTH_SHORT).show()
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
            ObjectAnimator.ofFloat(fabElectives, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 45f, 0f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }

    private fun openFab() {
        binding.apply {
            ObjectAnimator.ofFloat(fabMajor, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(fabElectives, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(fabMain, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen
    }
}