package cn.wsgwz.basemodule

import android.os.Bundle
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import cn.wsgwz.basemodule.interfaces.listeners.OnConnectivityChangeListener
import cn.wsgwz.basemodule.utilities.manager.ConnectivityChangeListenerManager
import cn.wsgwz.basemodule.utilities.WindowUtil
import io.reactivex.disposables.CompositeDisposable


open class BaseNetworkActivity : BaseActivity(), BaseNetworkWindowInterface, OnConnectivityChangeListener{
    override lateinit var compositeDisposable :CompositeDisposable
    private var loadingDialogFragment: LoadingDialogFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
        ConnectivityChangeListenerManager.getInstance().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        ConnectivityChangeListenerManager.getInstance().unregister(this)
    }


    override fun showLoadingDialog(isCancellable: Boolean) {
        if (loadingDialogFragment == null) {
            loadingDialogFragment = LoadingDialogFragment()
        }
        loadingDialogFragment?.also {
            it.isCancelable = isCancellable
            it.show(supportFragmentManager)
        }
    }

    override fun dismissLoadingDialog() {
        loadingDialogFragment?.also {
            it.dismiss()
        }
    }



}
