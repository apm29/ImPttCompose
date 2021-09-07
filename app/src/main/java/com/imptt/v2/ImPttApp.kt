package com.imptt.v2

import android.app.Application
import com.imptt.v2.utils.log.ReleaseTimberTree
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 *  author : ciih
 *  date : 2021/9/7 4:10 下午
 *  description :
 */
class ImPttApp:Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTimberTree())
        }
    }
}