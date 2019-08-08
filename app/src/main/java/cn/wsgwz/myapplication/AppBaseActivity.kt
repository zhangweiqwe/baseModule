package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.ViewStub
import androidx.annotation.StringRes
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.common.ToolbarManager

open class AppBaseActivity : BaseNetworkActivity() {



    companion object {
        private const val TAG = "AppBaseActivity"
    }

    val toolbar by lazy {
        ToolbarManager.with(this)
    }

}
