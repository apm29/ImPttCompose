package com.imptt.v2.base

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleObserver
import com.kylindev.pttlib.service.InterpttService
import java.util.ArrayList

/**
 *  author : ciih
 *  date : 2021/9/6 10:35 上午
 *  description :
 */
interface ToTalkConnection : ServiceConnection,LifecycleObserver {

    var mPttService: InterpttService?
    val toTalkServiceConnectionListener: ArrayList<(InterpttService) -> Unit>

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val localBinder = service as InterpttService.LocalBinder
        mPttService = localBinder.service
        toTalkServiceConnectionListener.forEach {
            it.invoke(localBinder.service)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        //SDK服务断开
    }
}