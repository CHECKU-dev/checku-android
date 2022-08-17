package com.yoon.nodeproject2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.yoon.nodeproject2.databinding.DialogNotificationDeleteBinding

class HomeNotificationDialog(
    val viewModel: HomeViewModel
) :
    DialogFragment() {

    private lateinit var binding: DialogNotificationDeleteBinding


   override fun onCreateView(
       inflater: LayoutInflater,
       container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View? {
       binding = DialogNotificationDeleteBinding.inflate(inflater, container, false)

       binding.lifecycleOwner = this
       binding.data = viewModel.dialogShowEvent.getValue()
       binding.viewModel = viewModel
       return binding.root
   }


}