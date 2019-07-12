package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.utilities.AndroidBug5497Workaround

open class AppBaseActivity : BaseNetworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun setContentView(view: View?) {
        super.setContentView(view)
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)

    }
}
