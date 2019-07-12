package cn.wsgwz.basemodule

import android.os.Bundle
import android.view.View
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseNetworkWindowInterface
import cn.wsgwz.basemodule.interfaces.listeners.OnConnectivityChangeListener
import cn.wsgwz.basemodule.utilities.manager.ConnectivityChangeListenerManager
import io.reactivex.disposables.CompositeDisposable

open class BaseNetworkFragment : BaseFragment(), BaseNetworkWindowInterface, OnConnectivityChangeListener{
    override lateinit var compositeDisposable:CompositeDisposable
    private var loadingDialogFragment: LoadingDialogFragment? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compositeDisposable = CompositeDisposable()
        ConnectivityChangeListenerManager.getInstance().register(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
        ConnectivityChangeListenerManager.getInstance().unregister(this)
    }

    override fun showLoadingDialog(isCancellable: Boolean) {
        if (loadingDialogFragment == null) {
            loadingDialogFragment = LoadingDialogFragment()
        }
        loadingDialogFragment?.also {
            it.isCancelable = isCancellable
            activity?.also { activity ->
                it.show(activity.supportFragmentManager)
            }
        }
    }

    override fun dismissLoadingDialog() {
        loadingDialogFragment?.also {
            it.dismiss()
        }
    }


}