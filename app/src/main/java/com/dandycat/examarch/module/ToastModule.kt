package com.dandycat.examarch.module

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class ToastModule @Inject constructor(private val mContext : Context) {
    fun showToast(msg : String) =
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
}