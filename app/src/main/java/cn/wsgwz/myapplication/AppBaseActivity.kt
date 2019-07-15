package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import cn.wsgwz.basemodule.BaseNetworkActivity
import cn.wsgwz.basemodule.utilities.AndroidBug5497Workaround
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class AppBaseActivity : BaseNetworkActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
