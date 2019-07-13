package cn.wsgwz.basemodule.interfaces

import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment


interface BaseNetworkWindowInterface : BaseWindowInterface, BaseRetrofitInterface {


    val loadingDialogFragment: LoadingDialogFragment

    fun onRefresh() {}


    fun onAvailable(network: Network) {}

    fun onLoginSuccess() {}

    fun onLogoutSuccess() {}




    fun AppCompatActivity.showLoading(isCancellable: Boolean = false) {
        loadingDialogFragment.isCancelable = isCancellable
        loadingDialogFragment.show(supportFragmentManager)
    }

    fun AppCompatActivity.dismissLoading() {
        loadingDialogFragment.dismiss()
    }

    fun Fragment.showLoading(isCancellable: Boolean = false) {
        loadingDialogFragment.isCancelable = isCancellable
        loadingDialogFragment.show(fragmentManager!!)
    }

    fun Fragment.dismissLoading() {
        loadingDialogFragment.dismiss()
    }


}