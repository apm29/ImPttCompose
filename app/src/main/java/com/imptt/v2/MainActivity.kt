package com.imptt.v2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import com.imptt.v2.ptt.BindableActivity
import com.imptt.v2.ptt.ToTalkServiceBinderObserver
import com.imptt.v2.ptt.PttObserver
import com.imptt.v2.ui.ComposeApp
import com.kylindev.pttlib.service.InterpttService
import java.lang.IllegalStateException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity(), BindableActivity {


    override val mServiceBinderObserver =
        ToTalkServiceBinderObserver(this, this::onToTalkServiceBind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeApp()
        }
    }

    private fun onToTalkServiceBind(service: InterpttService) {
        service.registerObserver(PttObserver())
    }

}


//根据Context获取绑定的SDKService
suspend fun Context.requireToTalkService() = suspendCoroutine<InterpttService> { continuation ->
    if (this is BindableActivity){
        if(!mServiceBinderObserver.isToTalkServiceInitialized){
            try {
                mServiceBinderObserver.rebindService{
                    continuation.resume(it)
                }
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        } else {
            continuation.resume(mServiceBinderObserver.mToTalkService)
        }
    } else {
        continuation.resumeWithException(IllegalStateException("错误的宿主Activity"))
    }
}