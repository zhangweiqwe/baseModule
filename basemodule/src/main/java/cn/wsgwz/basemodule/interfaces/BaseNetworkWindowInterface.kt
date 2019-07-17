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


    fun showLoading(isCancellable: Boolean = false) {
        loadingDialogFragment.isCancelable = isCancellable
        when (this) {
            is AppCompatActivity -> {
                loadingDialogFragment.show(supportFragmentManager)
            }
            is Fragment -> {
                loadingDialogFragment.show(fragmentManager!!)
            }
        }
    }


    fun dismissLoading() {
        loadingDialogFragment.dismiss()
    }

}