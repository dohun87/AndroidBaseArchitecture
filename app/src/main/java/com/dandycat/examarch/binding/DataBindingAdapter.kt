package com.dandycat.examarch.binding

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.domain.util.Logger
import com.dandycat.examarch.adapter.SearchListAdapter

@BindingAdapter("visibleNotSearch")
fun FrameLayout.visibleNoSearchData(notSearch : Boolean?){
    notSearch?.let {
        visibility = if(notSearch) View.VISIBLE
        else View.GONE
    }
}

@BindingAdapter("visibleLoading")
fun ProgressBar.visibleStatus(isVisible: Boolean?){
    isVisible?.let {
        visibility = if(isVisible) View.VISIBLE
                     else View.GONE
    }
}

@BindingAdapter("userProfileImg")
fun ImageView.setProfileImageView(url : String){
    Glide.with(this).load(url).centerCrop().into(this)
}

@BindingAdapter("setItems")
fun RecyclerView.setSelectItem(list : List<RepositoryEntity>?){
    Logger.d("setSelectItem")
    list?.let {
        (adapter as SearchListAdapter).submitList(list)
    }
}
