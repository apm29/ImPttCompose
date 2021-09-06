package com.imptt.v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.imptt.v2.base.PttServiceBinderObserver
import com.imptt.v2.base.ToTalkConnection
import com.imptt.v2.ui.ComposeApp
import com.imptt.v2.ui.theme.ImPttComposeTheme
import com.kylindev.pttlib.service.InterpttService
import java.util.*

class MainActivity : ComponentActivity(), ToTalkConnection {

    override var mPttService: InterpttService? = null
    override val toTalkServiceConnectionListener: ArrayList<(InterpttService) -> Unit> =
        arrayListOf()
    private val mPttServiceBinderObserver: PttServiceBinderObserver by lazy {
        PttServiceBinderObserver(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPttServiceBinderObserver.ensureCreated()
        setContent {
            ComposeApp()
        }
    }

}