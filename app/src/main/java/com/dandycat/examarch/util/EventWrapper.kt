package com.dandycat.examarch.util

import androidx.lifecycle.Observer

/**
 * 라이브데이터 한번만 동작시키기 위한 클래스 정의 모음
 * 라이브데이터의 경우 onPause 이후 onResume 시 연속 동작이 되기 때문에
 * 딱 한번만 호출하게 하기 위해 작업된 클래스
 */
open class Event<out T>(private val content : T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled() : T? {
        return if(hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent() : T = content
}

class SingleEventObserver<T>(private val onEventUnHandledContent : (T)->Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnHandledContent(value)
        }
    }
}