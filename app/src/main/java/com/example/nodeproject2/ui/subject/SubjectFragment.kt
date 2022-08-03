package com.example.nodeproject2.ui.subject

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

    companion object {
        fun newInstance() = SubjectFragment()
        const val TAG = "SubjectFragment"
    }

    override fun doViewCreated() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()
        observeRecyclerView()
    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            subjectAdapter.submitList(it.toMutableList()) {
                if (viewModel.paging.page.value == 1) {
                    binding.rvSubject.scrollToPosition(0)
                }
            }
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




}