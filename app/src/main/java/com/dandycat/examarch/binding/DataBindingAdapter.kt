package com.dandycat.examarch.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dandycat.examarch.listener.widget.EndlessScrollInterface
import com.dandycat.examarch.util.EndlessScrollListener

@BindingConversion
fun convertToVisibility(isVisible: Boolean) : Int {
    return if(isVisible) View.VISIBLE
    else View.GONE
}

@BindingAdapter("userProfileImg")
fun ImageView.setProfileImageView(url : String){
    Glide.with(this).load(url).centerCrop().into(this)
}

@BindingAdapter("addScrollListener")
fun RecyclerView.setScrollListener(listener : EndlessScrollInterface){
    val scrollListener = object : EndlessScrollListener(layoutManager as LinearLayoutManager){
        override fun onLoadMore() {
            listener.loadMoreData()
        }
    }
    addOnScrollListener(scrollListener)
}
