package cn.wsgwz.basemodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.UserManager

private const val TAG = "BaseWindowBroadcastReceiver"
class BaseWindowBroadcastReceiver(private val baseNetworkWindowInterface: BaseNetworkWindowInterface): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        LLog.d(TAG, "${hashCode()}" + intent.action)
        when (intent.action) {
            BaseConst.Action.USER_STATE_CHANGE -> {
                when (intent.getIntExtra(UserManager.USER_SATE_KEY,0)) {
                    UserManager.LOGIN_SUCCESS -> {
                        baseNetworkWindowInterface.onLoginSuccess()
                    }
                    UserManager.LOGOUT_SUCCESS -> {
                        baseNetworkWindowInterface.onLogoutSuccess()
                    }
                }
            }
        }
    }
}