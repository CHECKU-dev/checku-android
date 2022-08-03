package com.example.nodeproject2.ui.subject

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nodeproject2.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

@BindingAdapter("isLiked")
fun setLikeImg(
    imageView: ImageView,
    isLiked: Boolean
) {
    if(isLiked) imageView.setBackgroundResource(R.drawable.ic_drag_button)
    else imageView.setBackgroundResource(R.drawable.ic_checku_logo)
}