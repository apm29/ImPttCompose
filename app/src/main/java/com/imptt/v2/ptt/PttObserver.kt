package com.imptt.v2.ptt

import android.bluetooth.BluetoothDevice
import com.google.gson.Gson
import com.kylindev.pttlib.db.ChatMessageBean
import com.kylindev.pttlib.service.BaseServiceObserver
import com.kylindev.pttlib.service.InterpttService
import com.kylindev.pttlib.service.model.Channel
import com.kylindev.pttlib.service.model.Contact
import com.kylindev.pttlib.service.model.User
import com.kylindev.pttlib.utils.ServerProto
import timber.log.Timber

/**
 *  author : ciih
 *  date : 2020/10/22 7:32 PM
 *  description :
 */
open class PttObserver(
    private val tag:String? = "PttObserver"
) :BaseServiceObserver() {
    override fun onChannelAdded(channel: Channel) {
        super.onChannelAdded(channel)
        Timber.d("$tag.onChannelAdded")
        Timber.d("$tag:channel = [${channel}]")
    }

    override fun onAmrData(data: ByteArray?, length: Int, duration: Int) {
        super.onAmrData(data, length, duration)
        Timber.d("$tag.onAmrData")
    }

    override fun onChannelRemoved(channel: Channel) {
        super.onChannelRemoved(channel)
        Timber.d("$tag.onChannelRemoved")
        Timber.d("$tag:channel = [${channel}]")
    }

    override fun onChannelUpdated(channel: Channel) {
        super.onChannelUpdated(channel)
        Timber.d("$tag.onChannelUpdated")
    }

    override fun onConnectionStateChanged(state: InterpttService.ConnState) {
        super.onConnectionStateChanged(state)
        Timber.d("$tag.onConnectionStateChanged")
    }

    override fun onCurrentChannelChanged() {
        super.onCurrentChannelChanged()
        Timber.d("$tag.onCurrentChannelChanged")
    }

    override fun onCurrentUserUpdated() {
        super.onCurrentUserUpdated()
        Timber.d("$tag.onCurrentUserUpdated")
    }

    override fun onUserUpdated(user: User?) {
        super.onUserUpdated(user)
        Timber.d("$tag.onUserUpdated")
    }

    override fun onUserTalkingChanged(user: User?, talking: Boolean) {
        super.onUserTalkingChanged(user, talking)
        Timber.d("$tag.onUserTalkingChanged")
    }

    override fun onUserOrderCall(user: User?, p1: Boolean, p2: String) {
        super.onUserOrderCall(user, p1, p2)
        Timber.d("$tag.onUserOrderCall")
    }

    override fun onLocalUserTalkingChanged(user: User?, talking: Boolean) {
        super.onLocalUserTalkingChanged(user, talking)
        Timber.d("$tag.onLocalUserTalkingChanged user = [${user}], talking = [${talking}]")
    }

    override fun onNewVolumeData(volume: Short) {
        super.onNewVolumeData(volume)
    }

    override fun onPermissionDenied(reason: String, code: Int) {
        super.onPermissionDenied(reason, code)
        Timber.d("$tag.onPermissionDenied")
        Timber.d("$tag:reason = [${reason}], code = [${code}]")
    }

    override fun onRegisterResult(p0: Int) {
        super.onRegisterResult(p0)
        Timber.d("$tag.onRegisterResult")
    }

    override fun onForgetPasswordResult(p0: Boolean) {
        super.onForgetPasswordResult(p0)
        Timber.d("$tag.onForgetPasswordResult")
    }

    override fun onRejected(rejectType: ServerProto.Reject.RejectType) {
        super.onRejected(rejectType)
        Timber.d("$tag.onRejected")
    }

    override fun onMicStateChanged(p0: InterpttService.MicState) {
        super.onMicStateChanged(p0)
        Timber.d("$tag.onMicStateChanged")
    }

    override fun onHeadsetStateChanged(headState: InterpttService.HeadsetState) {
        super.onHeadsetStateChanged(headState)
        Timber.d("$tag.onHeadsetStateChanged")
        Timber.d("$tag:headState = [${headState}]")
    }

    override fun onScoStateChanged(p0: Int) {
        super.onScoStateChanged(p0)
        Timber.d("$tag.onScoStateChanged $p0")
    }

    override fun onTargetHandmicStateChanged(
        p0: BluetoothDevice,
        p1: InterpttService.HandmicState
    ) {
        super.onTargetHandmicStateChanged(p0, p1)
        Timber.d("$tag.onTargetHandmicStateChanged")
    }

    override fun onLeDeviceScanStarted(p0: Boolean) {
        super.onLeDeviceScanStarted(p0)
        Timber.d("$tag.onLeDeviceScanStarted")
    }

    override fun onLeDeviceFound(device: BluetoothDevice) {
        super.onLeDeviceFound(device)
        Timber.d("$tag.onLeDeviceFound")
    }

    override fun onTalkingTimerTick(duration: Int) {
        super.onTalkingTimerTick(duration)
        Timber.d("$tag.onTalkingTimerTick")
        Timber.d("$tag:duration = [${duration}]")
    }

    override fun onTalkingTimerCanceled() {
        super.onTalkingTimerCanceled()
        Timber.d("$tag.onTalkingTimerCanceled")
    }

    override fun onUserAdded(p0: User?) {
        super.onUserAdded(p0)
        Timber.d("$tag.onUserAdded")
    }

    override fun onUserRemoved(p0: User?) {
        super.onUserRemoved(p0)
        Timber.d("$tag.onUserRemoved")
    }

    override fun onChannelSearched(
        p0: Int,
        p1: String,
        p2: Boolean,
        p3: Boolean,
        p4: Int,
        p5: Int
    ) {
        super.onChannelSearched(p0, p1, p2, p3, p4, p5)
        Timber.d("$tag.onChannelSearched")
        Timber.d("$tag:p0 = [${p0}], p1 = [${p1}], p2 = [${p2}], p3 = [${p3}], p4 = [${p4}], p5 = [${p5}]")
    }

    override fun onShowToast(message: String) {
        super.onShowToast(message)
        Timber.d("$tag.onShowToast")
    }

    override fun onPlaybackChanged(channelId: Int, resId: Int, play: Boolean) {
        super.onPlaybackChanged(channelId, resId, play)
        Timber.d("$tag.onPlaybackChanged channelId = [${channelId}], resId = [${resId}], play = [${play}]")
    }

    override fun onRecordFinished(messageBean: ChatMessageBean) {
        super.onRecordFinished(messageBean)
        Timber.d("$tag.onRecordFinished ${Gson().toJson(messageBean)}")
    }

    /**
     * 其中sendPcmRecord是short[]数组，pcmRecordLength是数组长度
     */
    override fun onPcmRecordFinished(sendPcmRecord: ShortArray, pcmRecordLength: Int) {
        super.onPcmRecordFinished(sendPcmRecord, pcmRecordLength)
        Timber.d("$tag.onPcmRecordFinished")
    }

    override fun onInvited(p0: Channel) {
        super.onInvited(p0)
        Timber.d("$tag.onInvited")
    }

    override fun onUserSearched(p0: User?) {
        super.onUserSearched(p0)
        Timber.d("$tag.onUserSearched")
    }

    override fun onApplyContactReceived(p0: Boolean, p1: Contact) {
        super.onApplyContactReceived(p0, p1)
        Timber.d("$tag.onApplyContactReceived")
    }

    override fun onPendingContactChanged() {
        super.onPendingContactChanged()
        Timber.d("$tag.onPendingContactChanged")
    }

    override fun onContactChanged() {
        super.onContactChanged()
        Timber.d("$tag.onContactChanged")
    }

    override fun onPendingMemberChanged() {
        super.onPendingMemberChanged()
        Timber.d("$tag.onPendingMemberChanged")
    }

    override fun onSynced() {
        super.onSynced()
        Timber.d("$tag.onSynced")
    }

    override fun onVoiceToggleChanged(on: Boolean) {
        super.onVoiceToggleChanged(on)
        Timber.d("$tag.onVoiceToggleChanged on = [$on]")
    }

    override fun onMembersGot(p0: Int, p1: String) {
        super.onMembersGot(p0, p1)
        Timber.d("$tag.onMembersGot")
    }

    override fun onListenChanged(listen: Boolean) {
        super.onListenChanged(listen)
        Timber.d("$tag.onListenChanged")
    }

    override fun onApplyOrderResult(p0: Int, p1: Int, p2: String, p3: Boolean) {
        super.onApplyOrderResult(p0, p1, p2, p3)
        Timber.d("$tag.onApplyOrderResult")
    }

    override fun onGeneralMessageGot(p0: Int, p1: Int, p2: Int, p3: Int, p4: String) {
        super.onGeneralMessageGot(p0, p1, p2, p3, p4)
        Timber.d("$tag.onGeneralMessageGot")
    }

    override fun onBleButtonDown(down: Boolean) {
        super.onBleButtonDown(down)
        Timber.d("$tag.onBleButtonDown")
    }
}