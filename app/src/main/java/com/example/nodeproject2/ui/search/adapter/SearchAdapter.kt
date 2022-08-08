package com.example.nodeproject2.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.databinding.ItemRecyclerSearchBinding
import com.example.nodeproject2.databinding.ItemRecyclerSubjectBinding
import com.example.nodeproject2.ui.search.SearchViewModel
import com.example.nodeproject2.ui.list.subject.adapter.SubjectAdapter

class SearchAdapter(val viewModel: SearchViewModel) :
    ListAdapter<Subject, SearchAdapter.Holder>(SearchAdapter.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() - 1
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: SearchAdapter.Holder, position: Int) {
        val safePosition = holder.adapterPosition
        currentList[safePosition]?.let { holder.setData(it) }
    }


    inner class Holder(val binding: ItemRecyclerSearchBinding) :
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