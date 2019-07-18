package cn.wsgwz.myapplication

import android.os.Bundle
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.common.ToolbarManager

open class AppBaseActivity : BaseNetworkActivity() {

    val toolbar by lazy {
        ToolbarManager.with(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
