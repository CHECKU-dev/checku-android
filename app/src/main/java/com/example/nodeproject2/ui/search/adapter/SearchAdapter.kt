package com.example.nodeproject2.ui.search.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.databinding.ItemRecyclerSearchBinding
import com.example.nodeproject2.databinding.ItemRecyclerSearchLoadingBinding
import com.example.nodeproject2.ui.search.SearchViewModel
import com.example.nodeproject2.ui.syllabus.SyllabusActivity
import com.example.nodeproject2.widget.utils.SearchRecyclerViewType.CONTENT
import com.example.nodeproject2.widget.utils.SearchRecyclerViewType.LOADING

class SearchAdapter(val viewModel: SearchViewModel) :
    ListAdapter<Subject, RecyclerView.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            CONTENT -> {
                val binding = ItemRecyclerSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                Holder(binding)
            }
            else -> {
                val binding = ItemRecyclerSearchLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val safePosition = holder.adapterPosition
        currentList[safePosition]?.let { (holder as Holder).setData(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return if(currentList[position] == null) LOADING
        else CONTENT
    }


    inner class Holder(val binding: ItemRecyclerSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun setData(data: Subject) {
            binding.data = data
            binding.viewModel = viewModel
            binding.position = adapterPosition
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val intent = Intent(context, SyllabusActivity::class.java)
                intent.putExtra("subjectNumber", data.subjectNumber)
                intent.run { context.startActivity(this) }
            }
        }

    }

    inner class LoadingHolder(binding: ItemRecyclerSearchLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)


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