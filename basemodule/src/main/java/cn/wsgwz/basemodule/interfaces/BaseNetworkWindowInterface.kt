package cn.wsgwz.basemodule.interfaces

import io.reactivex.disposables.CompositeDisposable

interface BaseNetworkWindowInterface  : BaseWindowInterface {
    val compositeDisposable: CompositeDisposable
    fun onRefresh(){}

    fun showLoadingDialog(isCancellable: Boolean = false)

    fun dismissLoadingDialog()

    fun needRefresh():Boolean{
        return false
    }
}