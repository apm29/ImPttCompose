package com.imptt.v2.utils.log

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

/**
 *  author : ciih
 *  date : 2021/9/7 4:07 下午
 *  description :
 */
class ReleaseTimberTree: Timber.Tree() {
    @SuppressLint("LogNotTimber")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        Log.i("Timber",message)
    }
}