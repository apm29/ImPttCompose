package com.imptt.v2.service

import android.app.Service
import android.bluetooth.BluetoothManager
import android.content.*
import android.media.AudioManager
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.widget.Toast
import com.imptt.v2.base.CoroutineScopeContext
import com.itsmartreach.libzm.ZmCmdLink
import com.kylindev.pttlib.service.InterpttService
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine


/**
 *  author : ciih
 *  date : 2020/12/16 11:26 AM
 *  description :
 */
class TalkieMicService : Service(), CoroutineScopeContext {

    inner class LocalBinder : Binder() {
        val service: TalkieMicService
            get() = this@TalkieMicService
    }

    private val mBinder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    companion object {
        const val NOTIFICATION_ID = 23101
        const val NOTIFICATION_CHANNEL_ID = "肩咪连接服务-PTT"
        const val NOTIFICATION_CHANNEL_NAME = "智咪连接服务"
    }


    var mPttService: InterpttService? = null
    private var runBatteryRequest = true
    private val mAudioManager by lazy {
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    private val mBluetoothManager by lazy {
        getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }

    private val mPowerManager by lazy {
        getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    private var wakeLock:PowerManager.WakeLock? = null

    override fun onCreate() {
        super.onCreate()
        wakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "ptt:HandMicService")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        zmCmdLink
        showNotificationAndStartForeground()
        //绑定建立链接
        tryBindService(null)
        return START_STICKY
    }

    private fun showNotificationAndStartForeground() {
        startForeground(
            NOTIFICATION_ID, NotificationFactory.createNotification(
                this@TalkieMicService,
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                getNotificationContent(handMicState, handMicBatteryLevel)
            )
        )
    }

    private var onServiceConnectedCallBack : ((InterpttService)-> Unit)? = null

    private fun tryBindService(onServiceConnected: ((InterpttService) -> Unit)?) {
        if(onServiceConnected!=null){
            this.onServiceConnectedCallBack = onServiceConnected
        }
        bindService(
            Intent(this, InterpttService::class.java),
            mServiceConnection, BIND_IMPORTANT
        )
    }


    private val mServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            //链接上
            val localBinder = iBinder as InterpttService.LocalBinder
            mPttService = localBinder.service
            onServiceConnectedCallBack?.invoke(mPttService!!)
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            //断开链接
            startService(Intent(this@TalkieMicService, InterpttService::class.java))
            //重新绑定
            bindService(
                Intent(this@TalkieMicService, InterpttService::class.java),
                this, BIND_IMPORTANT
            )
        }
    }

    suspend fun requirePttService(): InterpttService {
        return suspendCancellableCoroutine<InterpttService> { continuation ->
            this.mPttService?.let { service: InterpttService ->
                continuation.resume(service) { throwable ->
                }
            } ?: tryBindService { service: InterpttService ->
                continuation.resume(service) { throwable ->
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        mPttService?.userPressUp()
        zmCmdLink.destroy()
        if(wakeLock?.isHeld == true){
            wakeLock?.release()
        }
        runBatteryRequest = false
    }

    fun isSppConnected(): Boolean {
        return zmCmdLink.isSppConnected
    }

    enum class HandMicState(val state: String) {
        IDLE("空闲"),
        RUNNING("未连接"),
        CONNECTED("肩咪按键已连接"),
        DISCONNECTED("肩咪按键已断开"),
        PRESSED_PTT("按键按下")
    }

    private fun getNotificationContent(state: HandMicState, level:Int): String {
        return when(state){
            HandMicState.DISCONNECTED, HandMicState.RUNNING -> state.state
            else -> "${state.state} ${toPercentage(level)}"
        }
    }

    private var handMicState: HandMicState = HandMicState.RUNNING
    private var handMicBatteryLevel:Int = 0
    private fun toPercentage(level: Int): String {
        if (level >= 3600) {
            val j = (level - 3600) / 4
            var i = j
            if (j > 100) i = 100
            val builder = StringBuilder("电量：")
            builder.append(i.toString())
            builder.append("%")
            return builder.toString()
        }
        val builder = StringBuilder()
        builder.append("电量低：")
        builder.append(level)
        return builder.toString()
    }

    fun requestBatteryLevel(){
        zmCmdLink.requestBatteryLevel()
    }

    private val zmCmdLink  by lazy {
        ZmCmdLink(this, object : ZmCmdLink.ZmEventListener {

            //sco
            override fun onScoStateChanged(sco: Boolean) {
                println("AudioRecord.onScoStateChanged sco = [${sco}]")
            }

            //spp
            override fun onSppStateChanged(spp: Boolean) {
                println("AudioRecord.onSppStateChanged state = [$spp]")
                if (spp) {
                    handMicState = HandMicState.CONNECTED
                    requestBatteryLevel()
                    showNotificationAndStartForeground()
                    Toast.makeText(
                        this@TalkieMicService,
                        "连接蓝牙肩咪成功",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    handMicState = HandMicState.DISCONNECTED
                    showNotificationAndStartForeground()
                    launch {
                        requirePttService().userPressUp()
                    }
                }
            }

            //用户按键
            override fun onUserEvent(event: ZmCmdLink.ZmUserEvent?) {
                println("AudioRecord.onUserEvent event = [${event}]")
                println("event = [${event}]")
                if (event == ZmCmdLink.ZmUserEvent.zmEventPttPressed) {
                    handMicState = HandMicState.PRESSED_PTT
                    showNotificationAndStartForeground()
                    launch {
                        requirePttService().userPressDown()
                    }
                } else if (event == ZmCmdLink.ZmUserEvent.zmEventPttReleased) {
                    handMicState = HandMicState.IDLE
                    showNotificationAndStartForeground()
                    launch {
                        requirePttService().userPressUp()
                    }
                }
            }

            override fun onBatteryLevelChanged(batteryLevel: Int) {
                handMicBatteryLevel = batteryLevel
                showNotificationAndStartForeground()
            }

            override fun onVolumeChanged(p0: Boolean) {
            }

            override fun onMuted() {

            }
        }, true)
    }

}