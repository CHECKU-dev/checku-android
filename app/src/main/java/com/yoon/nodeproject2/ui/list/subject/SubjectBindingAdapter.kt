package com.yoon.nodeproject2.ui.list.subject

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yoon.nodeproject2.R

@BindingAdapter("doScrollBottom")
fun RecyclerView.infiniteScrolls(doScrollBottom: () -> Unit) {
    this.addOnScrollListener((object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val itemTotalCount = recyclerView.adapter!!.itemCount - 1
            if (itemTotalCount == 0) return
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
            Log.d("infiniteScrolls", "스크롤 감지, $itemTotalCount $lastVisibleItemPosition")
            if (lastVisibleItemPosition == itemTotalCount) {
                Log.d("infiniteScrolls", "하단 감지, $itemTotalCount")
                doScrollBottom()
            }
        }
    }))
}

@BindingAdapter("isChecked")
fun checkChecked(imageView: ImageView, isMySubject: Boolean) {

    if(isMySubject)
        imageView.setBackgroundResource(R.drawable.ic_zzim_active)
    else
        imageView.setBackgroundResource(R.drawable.ic_zzim_inactive)
}

