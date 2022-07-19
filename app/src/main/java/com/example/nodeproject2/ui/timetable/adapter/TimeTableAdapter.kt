package com.example.nodeproject2.ui.timetable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.ResultGetSubjects
import com.example.nodeproject2.databinding.ItemRecyclerTimeTableBinding
import com.example.nodeproject2.ui.timetable.TimeTableViewModel

class TimeTableAdapter(val viewModel: TimeTableViewModel) :
    ListAdapter<ResultGetSubjects, TimeTableAdapter.Holder>(diffUtil) {


    //    interface OnItemClickListener {
//        fun onItemClick(v: View, data: InterestsListData, pos: Int)
//    }

    var listener: AdapterView.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerTimeTableBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        currentList[position]?.let { holder.setData(it) }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class Holder(val binding: ItemRecyclerTimeTableBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: ResultGetSubjects) {

            binding.data = data
            binding.viewModel = viewModel
            binding.executePendingBindings()

        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ResultGetSubjects>() {
            override fun areItemsTheSame(oldItem: ResultGetSubjects, newItem: ResultGetSubjects): Boolean {
                return oldItem.subjectNumber.equals(newItem.subjectNumber)
            }

            override fun areContentsTheSame(
                oldItem: ResultGetSubjects,
                newItem: ResultGetSubjects
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}