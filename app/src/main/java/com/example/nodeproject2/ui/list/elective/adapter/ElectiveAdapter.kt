package com.example.nodeproject2.ui.list.elective.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.databinding.ItemRecyclerElectiveBinding
import com.example.nodeproject2.ui.list.elective.ElectiveViewModel

class ElectiveAdapter(val viewModel: ElectiveViewModel) :
    ListAdapter<Subject, ElectiveAdapter.Holder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerElectiveBinding.inflate(
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


    inner class Holder(val binding: ItemRecyclerElectiveBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Subject) {

            binding.data = data
            binding.viewModel = viewModel
            binding.executePendingBindings()

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