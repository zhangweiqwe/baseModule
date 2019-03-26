package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import cn.wsgwz.basemodule.BaseActivity
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.utilities.AndroidBug5497Workaround

open class AppBaseActivity : BaseNetworkActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun setContentView(view: View?) {
        super.setContentView(view)
        AndroidBug5497Workaround.assistActivity(this, true)
    }


    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        AndroidBug5497Workaround.assistActivity(this, true)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        super.setContentView(view, params)
        AndroidBug5497Workaround.assistActivity(this, true)

    }
}
