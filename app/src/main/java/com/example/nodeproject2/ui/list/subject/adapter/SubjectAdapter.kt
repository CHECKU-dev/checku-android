package com.example.nodeproject2.ui.list.subject.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.R
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.databinding.ItemRecyclerSubjectBinding
import com.example.nodeproject2.ui.list.subject.SubjectViewModel
import com.example.nodeproject2.ui.syllabus.SyllabusActivity

class SubjectAdapter(val viewModel: SubjectViewModel) :
    ListAdapter<Subject, SubjectAdapter.Holder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerSubjectBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() - 1
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        println(position)
        val safePosition = holder.adapterPosition

        currentList[safePosition]?.let {
            holder.setData(it)

//            if (it.isMySubject) {
//            }

        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    inner class Holder(val binding: ItemRecyclerSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun setData(data: Subject) {
            println(data)
            binding.data = data
            binding.viewModel = viewModel
            binding.position = adapterPosition

//            if (data.isMySubject) {
//                binding.cbCheckButton.setBackgroundResource(R.drawable.ic_zzim_active)
//            }else {
//                binding.cbCheckButton.setBackgroundResource(R.drawable.ic_zzim_inactive)
//            }

            binding.executePendingBindings()

            itemView.setOnClickListener {
                val intent = Intent(context, SyllabusActivity::class.java)
                intent.putExtra("subjectNumber", data.subjectNumber.toString())
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