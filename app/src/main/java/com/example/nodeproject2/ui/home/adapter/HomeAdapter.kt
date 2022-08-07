package com.example.nodeproject2.ui.home.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.R
import com.example.nodeproject2.data.model.Schedule
import com.example.nodeproject2.databinding.ItemViewPagerHomeBinding
import com.example.nodeproject2.widget.utils.Utils

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



    inner class Holder(val binding: ItemViewPagerHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

//        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(data: Schedule) {
            binding.tvDeadline.text = Utils.getDeadline(data.deadline.toLocalDate())
            binding.data = data
//            if (selectedPosition == adapterPosition) {
//                binding.scheduleItemLayout.setBackgroundResource(R.drawable.bg_gray_color_round_20)
//            }else {
//                binding.scheduleItemLayout.setBackgroundResource(R.drawable.bg_gray_color_round_20)
//            }
        }
    }

    override fun getItemCount(): Int = schedule.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setData(schedule[position])
    }
}