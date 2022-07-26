package com.example.nodeproject2.ui.subject

import android.widget.ArrayAdapter
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

    override fun doViewCreated() {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        viewModel.getInitData()

        initMajorTextView()
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
            // 로딩창 삭제 등등..
        }

    }

    private fun initRecyclerView() {

        subjectAdapter = SubjectAdapter(viewModel)
        binding.rvSubject.adapter = subjectAdapter

    }

    private fun initMajorTextView() {
        val departmentArray = resources.getStringArray(R.array.department_list)

        val adapter = ArrayAdapter<String>(
            activity!!.applicationContext,
            android.R.layout.simple_dropdown_item_1line,
            departmentArray
        )

        val majorTextView = binding.majorAutoCompleteTv

        majorTextView.setAdapter(adapter)

    }


}