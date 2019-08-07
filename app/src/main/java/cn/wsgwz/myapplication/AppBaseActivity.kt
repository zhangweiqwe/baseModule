package cn.wsgwz.myapplication

import android.os.Bundle
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.WindowUtil
import cn.wsgwz.common.ToolbarManager
import io.reactivex.disposables.CompositeDisposable

open class AppBaseActivity : BaseNetworkActivity() {


    companion object {
        private const val TAG = "AppBaseActivity"
    }


    val toolbar by lazy {
        ToolbarManager.with(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}
