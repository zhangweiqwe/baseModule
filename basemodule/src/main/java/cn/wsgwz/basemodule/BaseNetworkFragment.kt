package cn.wsgwz.basemodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.NetworkUtil
import cn.wsgwz.basemodule.utilities.manager.UserManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseNetworkFragment : BaseFragment(), BaseNetworkWindowInterface {
    companion object {
        private const val TAG = "BaseNetworkFragment"
    }

    override lateinit var compositeDisposable: CompositeDisposable
    override val loadingDialogFragment by lazy {
        LoadingDialogFragment()
    }


    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            LLog.d(TAG, "${hashCode()}" + intent.action)
            when (intent.action) {
                BaseConst.Action.USER_STATE_CHANGE -> {
                    when (intent.getSerializableExtra(UserManager.USER_SATE_KEY) as UserManager.UserState) {
                        UserManager.UserState.LOGIN_SUCCESS -> {
                            onLoginSuccess()
                        }
                        UserManager.UserState.LOGOUT_SUCCESS -> {
                            onLogoutSuccess()
                        }
                    }
                }
            }
        }
    }


    @Inject
    internal lateinit var connectivityManager: ConnectivityManager

    @Inject
    internal lateinit var networkRequest: NetworkRequest

    private val cmNetworkCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    this@BaseNetworkFragment.onAvailable(network)
                    LLog.d(TAG, "onAvailable ${NetworkUtil.isConnected(context)} ")
                }
            }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction(BaseConst.Action.USER_STATE_CHANGE)
        })
        connectivityManager.requestNetwork(networkRequest, cmNetworkCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(broadcastReceiver)
        connectivityManager.unregisterNetworkCallback(cmNetworkCallback)
    }


}