package cn.wsgwz.myapplication

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_test_exit.*

class TestExitActivity : AppBaseActivity(), View.OnClickListener {
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button0 -> {
                onStop()
            }
            R.id.button1 -> {
                System.exit(0)
            }
            R.id.button2 -> {
                throw Exception()
            }
            R.id.button3 -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_exit)

        button0.setOnClickListener(this)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
    }
}
