package com.dandycat.examarch.listener.widget

/**
 * RecyclerView Scroll -> ViewModel 연결 시키기 위한 인터페이스
 * 하기 인터페이스를 추가한 이유는 ViewModel 내에서도 리스트를 받아오는 로직이 없기 때문에
 */
interface EndlessScrollInterface {
    fun loadMoreData()
}