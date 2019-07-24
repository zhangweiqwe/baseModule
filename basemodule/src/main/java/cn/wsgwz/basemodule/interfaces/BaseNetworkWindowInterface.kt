package cn.wsgwz.basemodule.interfaces

import android.content.Context
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.BaseFragment
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.BaseNetworkFragment
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment


interface BaseNetworkWindowInterface : BaseWindowInterface, BaseRetrofitInterface ,BaseLoadingDialogFragmentInterface{


    fun onRefresh() {}


    fun onAvailable(network: Network) {}

    fun onLoginSuccess() {}

    fun onLogoutSuccess() {}




}