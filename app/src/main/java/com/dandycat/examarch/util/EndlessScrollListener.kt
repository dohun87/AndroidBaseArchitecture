package com.dandycat.examarch.util

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import javax.inject.Inject

/**
 * 무한스크롤 리스너 클래스
 * 링크를 이용하여 참조, 재 구축 진행
 * @author dohun8832
 * @see https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews-and-RecyclerView
 */
abstract class EndlessScrollListener : RecyclerView.OnScrollListener {

    companion object {
        private val LIMITCOUNT = 2 // 변경 가능
    }

    private var mManager : RecyclerView.LayoutManager

    private var visibleThreshold = 0
    private var previousTotalItemCount = 0
    private var isApiCall = true

    constructor(manager : LinearLayoutManager) {
        mManager = manager
        visibleThreshold = LIMITCOUNT
    }

    constructor(manager : GridLayoutManager) {
        mManager = manager
        visibleThreshold = LIMITCOUNT * 2
    }

    constructor(manager : StaggeredGridLayoutManager){
        mManager = manager
        visibleThreshold = LIMITCOUNT * 2
    }


    fun resetApiCall(){
        previousTotalItemCount = 0
        isApiCall = true
    }

    /**
     * StaggeredGridLayoutManager 일시에 대한 마지막 포지션 구하기 위한 메소드
     * StaggeredGridLayoutManager의 경우 지그재그 형태여서, 해당 마지막이 정상적으로 표시 안될 수 있어서,
     * 해당 로직을 이용 마지막 포지션 값을 추출하는것 같다
     */
    private fun getLastVisibleItem(lastvisiblePositions : IntArray) : Int {
        var maxSize = 0
        for(i in lastvisiblePositions.indices){
            if(i==0) maxSize = lastvisiblePositions[i]
            else if(lastvisiblePositions[i] > maxSize) {
                maxSize = lastvisiblePositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        var lastItemPosition = 0
        val totalItemCount = mManager.itemCount
        when(mManager){
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (mManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }

            is GridLayoutManager -> {
                lastItemPosition =
                    (mManager as GridLayoutManager).findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                lastItemPosition =
                    (mManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }

        if(totalItemCount < previousTotalItemCount){
            previousTotalItemCount = totalItemCount
            if(totalItemCount ==0 ) isApiCall = true
        }

        if(isApiCall && totalItemCount > previousTotalItemCount){
            isApiCall = false
            previousTotalItemCount = totalItemCount
        }

        if(!isApiCall && lastItemPosition + visibleThreshold > totalItemCount) {
            onLoadMore()
            isApiCall = true
        }
    }

    abstract fun onLoadMore()
}