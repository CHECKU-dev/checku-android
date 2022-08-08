package com.example.nodeproject2.ui.timetable.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.databinding.ItemRecyclerTimeTableBinding
import com.example.nodeproject2.ui.syllabus.SyllabusActivity
import com.example.nodeproject2.ui.timetable.TimeTableViewModel
import kotlinx.android.synthetic.main.item_recycler_time_table.view.*

class TimeTableAdapter(val viewModel: TimeTableViewModel) :
    ListAdapter<Subject, TimeTableAdapter.Holder>(diffUtil) {

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
        holder.itemView.item_time_table_layout.translationX = 0f
        currentList[position]?.let { holder.setData(it) }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class Holder(val binding: ItemRecyclerTimeTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun setData(data: Subject) {
            binding.data = data
            binding.viewModel = viewModel
            binding.itemTimeTableLayout.setOnClickListener {
                val intent = Intent(context, SyllabusActivity::class.java)
                intent.putExtra("subjectNumber", data.subjectNumber)
                intent.run { context.startActivity(this) }
            }

        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Subject>() {
            override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
                return oldItem.subjectNumber.equals(newItem.subjectNumber)
            }

            override fun areContentsTheSame(
                oldItem: Subject,
                newItem: Subject
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}