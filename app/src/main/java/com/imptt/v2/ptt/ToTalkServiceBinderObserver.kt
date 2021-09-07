package com.imptt.v2.ptt

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.kylindev.pttlib.service.InterpttService
import timber.log.Timber

/**
 *  author : ciih
 *  date : 2020/11/16 6:42 PM
 *  description :
 */
class ToTalkServiceBinderObserver(
    private val context: ComponentActivity,
    private val onServiceBind: (InterpttService)->Unit
) : LifecycleObserver {

    lateinit var mToTalkService:InterpttService
    private val mBindListeners: ArrayList<(InterpttService)->Unit> = arrayListOf()
    val isToTalkServiceInitialized
     get() = ::mToTalkService.isInitialized

    private var mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName,
            binder: IBinder
        ) {
            val localBinder: InterpttService.LocalBinder =
                binder as InterpttService.LocalBinder
            mToTalkService = localBinder.service
            onServiceBind(mToTalkService)
            mBindListeners.forEach {
                it.invoke(mToTalkService)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }

    private var mBound = false
    private val serviceIntent by lazy { Intent(context, InterpttService::class.java) }

    init {
        context.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun onCreate() {
        Timber.d("ToTalkServiceBinderObserver.onCreate")
        context.startService(serviceIntent)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun onStart() {
        Timber.d("ToTalkServiceBinderObserver.onStart")
        rebindService()
    }

    fun rebindService(onBind:((InterpttService)->Unit)?=null) {
        onBind?.let {
            mBindListeners.add(it)
        }
        mBound = context.bindService(serviceIntent, mServiceConnection, Service.BIND_IMPORTANT or Service.BIND_AUTO_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        if (mBound) {
            context.unbindService(mServiceConnection)
        }
        mBound = false
        context.lifecycle.removeObserver(this)
    }

}