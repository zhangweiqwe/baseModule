package cn.wsgwz.basemodule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Bundle
import android.view.View
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


open class BaseNetworkActivity : BaseActivity(), BaseNetworkWindowInterface {
    companion object {
        private const val TAG = "BaseNetworkActivity"
    }

    val progressLayout by lazy {
        progress_layout
    }

    private lateinit var compositeDisposable: CompositeDisposable
    private val loadingDialogFragment by lazy {
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


    private val connectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val cmNetworkCallback =
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    this@BaseNetworkActivity.onAvailable(network)
                    LLog.d(TAG, "onAvailable ${NetworkUtil.isConnected(this@BaseNetworkActivity)} ")
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter().apply {
            addAction(BaseConst.Action.USER_STATE_CHANGE)
        })
        connectivityManager.requestNetwork(NetworkRequest.Builder().build(), cmNetworkCallback)


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        connectivityManager.unregisterNetworkCallback(cmNetworkCallback)
    }

    fun Disposable.add(): Disposable {
        compositeDisposable.add(this)
        return this
    }


    fun showLoading(isCancellable: Boolean = false) {
        loadingDialogFragment.isCancelable = isCancellable
        loadingDialogFragment.show(supportFragmentManager)
    }

    fun dismissLoading() {
        loadingDialogFragment.dismiss()
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
