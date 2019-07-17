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
import io.reactivex.disposables.Disposable
import javax.inject.Inject

open class BaseNetworkFragment : BaseFragment(), BaseNetworkWindowInterface {
    companion object {
        private const val TAG = "BaseNetworkFragment"
    }

    private lateinit var mRequestCompositeDisposable: CompositeDisposable
    override val requestCompositeDisposable: CompositeDisposable
        get() = mRequestCompositeDisposable

    override val loadingDialogFragment by lazy {
        LoadingDialogFragment()
    }


    private val broadcastReceiver by BaseWindowBroadcastReceiverDelegate()


    private val connectivityManager by lazy {
        context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val cmNetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                this@BaseNetworkFragment.onAvailable(network)
                LLog.d(TAG, "onAvailable ${NetworkUtil.isConnected(context)} ")
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRequestCompositeDisposable = CompositeDisposable()
        LocalBroadcastManager.getInstance(context!!).registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction(BaseConst.Action.USER_STATE_CHANGE)
        })
        connectivityManager.requestNetwork(NetworkRequest.Builder().build(), cmNetworkCallback)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRequestCompositeDisposable.dispose()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(broadcastReceiver)
        connectivityManager.unregisterNetworkCallback(cmNetworkCallback)
    }


}