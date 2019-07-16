package cn.wsgwz.basemodule

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import cn.wsgwz.basemodule.utilities.WindowUtil
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


open class BaseActivity : AppCompatActivity(), BaseWindowInterface, HasAndroidInjector {
    companion object {
        private const val TAG = "BaseActivity"
    }

    private val locationPoint by lazy {
        Point()
    }
    private var proportionX = 0
    private var lastActionDownTime = 0L

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


    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (!BuildConfig.DEBUG) {
            return super.dispatchTouchEvent(event)
        }

        if (event.action === MotionEvent.ACTION_DOWN) {
            lastActionDownTime = System.currentTimeMillis()
        } else if (event.action == MotionEvent.ACTION_UP) {
            if ((System.currentTimeMillis() - lastActionDownTime) < 800 && event.rawX - locationPoint.x > 50 && locationPoint.x < proportionX) {
                finish()
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
