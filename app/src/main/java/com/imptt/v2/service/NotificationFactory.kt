package com.imptt.v2.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.imptt.v2.MainActivity
import com.imptt.v2.R

/**
 *  author : apm29[ciih]
 *  date : 2020/9/11 11:15 AM
 *  description :
 */
object NotificationFactory {
    fun createNotification(
        context: Context,
        channelId: String,
        channelTitle: String,
        channelContent: String
    ): Notification {
        return builder(context, channelId, channelTitle)
            .setContentTitle(channelTitle)
            .setContentText(channelContent)
            .setContentIntent(createContentIntent(context))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setWhen(System.currentTimeMillis())
            .setSound(null,null)
            .build()
    }

    private fun createContentIntent(context: Context): PendingIntent {
        return PendingIntent.getActivity(
            context, 0,
            Intent(context, MainActivity::class.java).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
                addCategory(Intent.CATEGORY_LAUNCHER)
                action = Intent.ACTION_MAIN
            }, 0
        )
    }

    private fun builder(
        context: Context,
        channelId: String,
        channelName: String
    ): Notification.Builder {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    setSound(null,null)
                }
            )
        }
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }
    }
}