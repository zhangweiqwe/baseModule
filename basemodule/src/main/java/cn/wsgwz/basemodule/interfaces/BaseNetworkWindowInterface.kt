package cn.wsgwz.basemodule.interfaces


interface BaseNetworkWindowInterface  : BaseWindowInterface ,BaseRetrofitInterface{
    fun onRefresh(){}

    fun showLoadingDialog(isCancellable: Boolean = false)

    fun dismissLoadingDialog()

}