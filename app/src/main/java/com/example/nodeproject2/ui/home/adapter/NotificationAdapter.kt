package com.example.nodeproject2.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.data.model.Notification
import com.example.nodeproject2.databinding.ItemRecyclerNotificationBinding
import com.example.nodeproject2.ui.home.HomeViewModel

class NotificationAdapter(val viewModel: HomeViewModel) :
    ListAdapter<Notification, NotificationAdapter.Holder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerNotificationBinding.inflate(
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


    inner class Holder(val binding: ItemRecyclerNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(data: Notification) {

            binding.data = data
            binding.viewModel = viewModel
            binding.executePendingBindings()

        }

    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem.subjectNumber.equals(newItem.subjectNumber)
            }

            override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
                return oldItem == newItem
            }
        }
    }


}