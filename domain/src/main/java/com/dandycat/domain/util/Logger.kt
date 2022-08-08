package com.dandycat.domain.util

import android.util.Log
import com.dandycat.domain.BuildConfig

/**
 * 로그에 대한 커스텀 클래스
 * 스택트레이스를 이용 찾아 갈 수 있게 적용
 */
object Logger {

    private const val TAG = "EXAMARCH"

    @JvmStatic
    fun v(msg : String) {
        if(!BuildConfig.DEBUG) return
        Log.v(tag(),msg)
    }
    @JvmStatic
    fun i(msg : String) {
        if(!BuildConfig.DEBUG) return
        Log.i(tag(),msg)
    }
    @JvmStatic
    fun d(msg : String) {
        if(!BuildConfig.DEBUG) return
        Log.d(tag(),msg)
    }
    @JvmStatic
    fun w(msg : String) {
        if(!BuildConfig.DEBUG) return
        Log.w(tag(),msg)
    }
    @JvmStatic
    fun e(msg : String) {
        if(!BuildConfig.DEBUG) return
        Log.e(tag(),msg)
    }

    private fun tag() : String {
        //val trace = Thread.currentThread().stackTrace[level]
        val trace = Exception().stackTrace[2]
        val fileName = trace.fileName
        val lineNumber = trace.lineNumber
        val methodName = trace.methodName
        return "$TAG# ($fileName:$lineNumber) [method : $methodName]"
    }
}