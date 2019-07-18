package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.View
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.common.ToolbarManager

open class AppBaseActivity : BaseNetworkActivity() {

    val toolbar by lazy {
        ToolbarManager.with(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    /*fun showLoading() = progressLayout.showLoading()
    fun showContent() = progressLayout.showContent()


    fun showEmpty(l: (View) -> Unit) = progressLayout.showEmpty { l(it) }

    fun showError(l: (View) -> Unit) = progressLayout.showError { l(it) }*/

}
