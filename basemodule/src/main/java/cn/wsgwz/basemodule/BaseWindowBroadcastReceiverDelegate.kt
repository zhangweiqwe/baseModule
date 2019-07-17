package cn.wsgwz.basemodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.UserManager
import kotlin.reflect.KProperty

internal class BaseWindowBroadcastReceiverDelegate {
    companion object {
        private const val TAG = "BaseWindowBroadcastReceiverDelegate"
    }

    private var broadcastReceiver: BroadcastReceiver? = null


    operator fun getValue(baseNetworkWindowInterface: BaseNetworkWindowInterface, property: KProperty<*>): BroadcastReceiver {


        return broadcastReceiver.let {
            it ?: object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    LLog.d(TAG, "${hashCode()}" + intent.action)
                    when (intent.action) {
                        BaseConst.Action.USER_STATE_CHANGE -> {
                            when (intent.getSerializableExtra(UserManager.USER_SATE_KEY) as UserManager.UserState) {
                                UserManager.UserState.LOGIN_SUCCESS -> {
                                    baseNetworkWindowInterface.onLoginSuccess()
                                }
                                UserManager.UserState.LOGOUT_SUCCESS -> {
                                    baseNetworkWindowInterface.onLogoutSuccess()
                                }
                            }
                        }
                    }
                }
            }.apply { broadcastReceiver = this }
        }
    }
}