package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.MotionEvent


private const val TAG = "TestTouchActivity"

class TestTouchActivity : AppBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_touch)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        log("onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        log("dispatchTouchEvent")
        return super.dispatchTouchEvent(event)
    }
}
