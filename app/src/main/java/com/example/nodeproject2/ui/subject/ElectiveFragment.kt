package com.example.nodeproject2.ui.subject

import androidx.fragment.app.viewModels
import com.example.nodeproject2.base.BaseFragment
import com.example.nodeproject2.databinding.FragmentElectiveBinding
import com.example.nodeproject2.ui.subject.adapter.ElectiveAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectiveFragment : BaseFragment<FragmentElectiveBinding>(com.example.nodeproject2.R.layout.fragment_elective) {

    private val viewModel by viewModels<ElectiveViewModel>()
    private lateinit var electiveAdapter: ElectiveAdapter

    companion object {
        fun newInstance() = ElectiveFragment()
        const val TAG = "ElectiveFragment"
    }

    override fun doViewCreated() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initRecyclerView()
        observeRecyclerView()
    }

    private fun observeRecyclerView() {
        viewModel.subjectList.observe(viewLifecycleOwner) {
            electiveAdapter.submitList(it)
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
        electiveAdapter = ElectiveAdapter(viewModel)
        binding.rvSubject.adapter = electiveAdapter
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(hidden) {
        }else {
            viewModel.getSubjectData()
        }
    }

}