package cn.wsgwz.basemodule.interfaces

import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.BaseFragment
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.BaseNetworkFragment
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment


interface BaseNetworkWindowInterface : BaseWindowInterface, BaseRetrofitInterface {

    val loadingDialogFragment: LoadingDialogFragment

    fun onRefresh() {}


    fun onAvailable(network: Network) {}

    fun onLoginSuccess() {}

    fun onLogoutSuccess() {}


    fun BaseNetworkWindowInterface.showLoading(isCancellable: Boolean = false) {
        loadingDialogFragment.isCancelable = isCancellable
        when (this) {
            is BaseNetworkActivity -> {
                loadingDialogFragment.show(supportFragmentManager)
            }
            is BaseFragment -> {
                loadingDialogFragment.show(fragmentManager!!)
            }
        }
    }


    fun BaseNetworkWindowInterface.dismissLoading() {
        loadingDialogFragment.dismiss()
    }

}