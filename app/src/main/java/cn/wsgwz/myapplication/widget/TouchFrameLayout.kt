package cn.wsgwz.myapplication.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import cn.wsgwz.basemodule.utilities.LLog

private const val TAG = "TouchFrameLayout"
class TouchFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LLog.d(TAG,"onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        LLog.d(TAG,"onInterceptTouchEvent")
        return super.onInterceptTouchEvent(ev)
    }



    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LLog.d(TAG,"dispatchTouchEvent")
        return super.dispatchTouchEvent(ev)
    }

}