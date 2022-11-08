package com.dandycat.examarch.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.domain.util.Logger
import com.dandycat.examarch.adapter.SearchListAdapter
import com.dandycat.examarch.listener.RepoSelectInterface
import com.dandycat.examarch.util.EndlessScrollListener
import com.dandycat.examarch.viewmodel.SearchViewModel

@BindingConversion
fun convertToVisibility(isVisible: Boolean) : Int {
    return if(isVisible) View.VISIBLE
    else View.GONE
}

@BindingAdapter("userProfileImg")
fun ImageView.setProfileImageView(url : String){
    Glide.with(this).load(url).centerCrop().into(this)
}

@BindingAdapter("setItems")
fun RecyclerView.setSelectItem(list : List<RepositoryEntity>?){
    Logger.d("setSelectItem - listSize : ${list?.size}")
    adapter?.let { // 어댑터가 있을 경우 데이터를 밀어넣어준다.
        list?.let {
            (adapter as SearchListAdapter).submitList(list)
        }
    }?: kotlin.run { // 만약 어댑터가 Null인 경우(초기화 안되었을 경우) 초기화 후 데이터를 넣어준다.
        val adapter = SearchListAdapter().apply {
            list?.let {
                this.submitList(it)
            }
        }
        this.adapter = adapter
    }
}

@BindingAdapter("scrollListener")
fun RecyclerView.setEndlessScrollListener(vm : SearchViewModel){
    val scrollListener = object : EndlessScrollListener(layoutManager as LinearLayoutManager) {
        override fun onLoadMore() {
            vm.addNextPage()
        }
    }
    addOnScrollListener(scrollListener)
}

@BindingAdapter("setSelectRepo")
fun RecyclerView.selectRepoModel(vm : SearchViewModel){
    adapter?.let {
        (adapter as SearchListAdapter).addSelectListener(object : RepoSelectInterface{
            override fun selectRepoModel(entity: RepositoryEntity) {
                vm.setRepoModel(entity)
            }
        })
    }
}
