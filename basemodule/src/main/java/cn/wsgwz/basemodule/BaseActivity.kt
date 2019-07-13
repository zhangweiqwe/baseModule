package cn.wsgwz.basemodule

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import cn.wsgwz.basemodule.utilities.WindowUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


open class BaseActivity : AppCompatActivity(), BaseWindowInterface, HasAndroidInjector {
    private var loadingDialogFragment: LoadingDialogFragment? = null

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        BaseApplication.addActivity(this)
        WindowUtil.setStatusBarTransparent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.removeActivity(this)
    }


    override fun toast(charSequence: CharSequence?) {
        Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        toast(getString(resId))
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }
}
