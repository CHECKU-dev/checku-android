package com.example.nodeproject2.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.R
import com.example.nodeproject2.data.model.Schedule
import com.example.nodeproject2.databinding.ItemViewPagerHomeBinding

class HomeAdapter(val context: Context, val schedule: List<Schedule>) : RecyclerView.Adapter<HomeAdapter.Holder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        return Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_view_pager_home,
                parent,
                false
            )
        )
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.Holder {
//
//
//        val binding = ItemViewPagerHomeBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//
//        return Holder(binding)
//    }

    inner class Holder(val binding: ItemViewPagerHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: Schedule) {
            println(data)
            binding.data = data
        }

    }

    override fun getItemCount(): Int = schedule.size
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(schedule[position])
    }
}