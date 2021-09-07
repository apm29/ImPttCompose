package com.imptt.v2.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.imptt.v2.service.TalkieMicService
import com.kylindev.pttlib.LibConstants
import com.kylindev.pttlib.service.InterpttService

import kotlin.concurrent.thread

/**
 *  author : ciih
 *  date : 2021/1/11 3:40 PM
 *  description :
 */
class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //延时一段时间再启动
        thread {
            try {
                Thread.sleep(5000)
                val serviceIntent = Intent(context, InterpttService::class.java)
                val handMicIntent = Intent(context, TalkieMicService::class.java)
                //自动启动service，在Service的实现里判断，如果是自动启动的，则自动登录
                serviceIntent.action = LibConstants.ACTION_AUTO_LAUNCH
                if (Build.VERSION.SDK_INT >= 26) {
                    context.startForegroundService(serviceIntent)
                    context.startForegroundService(handMicIntent)
                } else {
                    context.startService(serviceIntent)
                    context.startService(handMicIntent)
                }
            } catch (e: Exception) {

            }
        }
    }
}