package cn.wsgwz.basemodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import io.reactivex.disposables.CompositeDisposable
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.NetworkUtil
import cn.wsgwz.basemodule.utilities.manager.UserManager
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressConstraintLayout
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_base_network.*
import javax.inject.Inject
import kotlin.reflect.KProperty


open class BaseNetworkActivity : BaseActivity(), BaseNetworkWindowInterface {
    companion object {
        private const val TAG = "BaseNetworkActivity"
    }

    val progressLayout by lazy {
        progress_layout
    }

    private lateinit var mRequestCompositeDisposable: CompositeDisposable
    override val requestCompositeDisposable: CompositeDisposable
        get() = mRequestCompositeDisposable

    override val loadingDialogFragment by lazy {
        LoadingDialogFragment()
    }


    private val broadcastReceiver by BaseWindowBroadcastReceiverDelegate()

    private val connectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val cmNetworkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                this@BaseNetworkActivity.onAvailable(network)
                LLog.d(TAG, "onAvailable ${NetworkUtil.isConnected(this@BaseNetworkActivity)} ")
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRequestCompositeDisposable = CompositeDisposable()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction(BaseConst.Action.USER_STATE_CHANGE)
        })
        connectivityManager.requestNetwork(NetworkRequest.Builder().build(), cmNetworkCallback)

        LLog.d(TAG, "${hashCode()} ${broadcastReceiver} onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        mRequestCompositeDisposable.dispose()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        connectivityManager.unregisterNetworkCallback(cmNetworkCallback)
        LLog.d(TAG, "${hashCode()} ${broadcastReceiver} onDestroy")
    }


    fun setCustomContentView(layoutResID: Int) {
        setContentView(R.layout.activity_base_network)
        layoutInflater.inflate(layoutResID, progressLayout, true)
    }

    fun setCustomContentView(view: View) {
        setContentView(R.layout.activity_base_network)
        progressLayout.addView(view)
    }
}


