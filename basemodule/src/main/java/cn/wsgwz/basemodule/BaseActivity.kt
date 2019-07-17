package cn.wsgwz.basemodule

import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.LLog
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
    private val proportionX by lazy {
        resources.displayMetrics.widthPixels / 30
    }
    private var lastActionDownTime = 0L

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        BaseApplication.addActivity(this)
        WindowUtil.setStatusBarTransparent(this)

        LLog.d(TAG,"${androidInjector}")
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
        if (BuildConfig.DEBUG) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    locationPoint.x = event.rawX.toInt()
                    locationPoint.y = event.rawY.toInt()
                    lastActionDownTime = System.currentTimeMillis()
                }
                MotionEvent.ACTION_UP -> {
                    if ((System.currentTimeMillis() - lastActionDownTime) < 800 && event.rawX - locationPoint.x > DensityUtil.dp2px(50f) && locationPoint.x < proportionX&&event.rawY-locationPoint.y<DensityUtil.dp2px(100f)) {
                        finish()
                    }
                }
            }
        }


        return super.dispatchTouchEvent(event)
    }
}
