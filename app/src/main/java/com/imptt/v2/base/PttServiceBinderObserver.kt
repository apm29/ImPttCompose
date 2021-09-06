package com.imptt.v2.base

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kylindev.pttlib.service.InterpttService

/**
 *  author : ciih
 *  date : 2020/11/16 6:42 PM
 *  description :
 */
class PttServiceBinderObserver(
    private val context: ComponentActivity,
    private val serviceConnection: ServiceConnection
) : LifecycleObserver {

    private var mBound = false
    private val serviceIntent by lazy { Intent(context, InterpttService::class.java) }

    init {
        context.lifecycle.addObserver(this)
    }

    fun ensureCreated(){}

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        context.startService(serviceIntent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        tryBindService()
    }

    fun tryBindService() {
        mBound = context.bindService(serviceIntent, serviceConnection, Service.BIND_IMPORTANT or Service.BIND_AUTO_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        if (mBound) {
            context.unbindService(serviceConnection)
        }
        mBound = false
        context.lifecycle.removeObserver(this)
    }

}