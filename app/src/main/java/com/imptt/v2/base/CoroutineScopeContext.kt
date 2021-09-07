package com.imptt.v2.base

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 *  author : ciih
 *  date : 2020/11/16 6:34 PM
 *  description :
 */
interface CoroutineScopeContext:CoroutineScope {
    private val mExceptionHandler: CoroutineExceptionHandler
        get() = CoroutineExceptionHandler { _, throwable ->
            Timber.e("COROUTINE EXCEPTION:")
            Timber.d(throwable)
            throwable.printStackTrace()
        }

    //SupervisorJob在子协程抛出异常时不会被取消
    val mJob
        get() = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + mJob + mExceptionHandler

    val coroutineIoContext:CoroutineContext
        get() = Dispatchers.IO +mJob + mExceptionHandler
}