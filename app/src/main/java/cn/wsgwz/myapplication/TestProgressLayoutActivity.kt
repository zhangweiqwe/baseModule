package cn.wsgwz.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_test_progress_layout.*

class TestProgressLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_progress_layout)

        progress_layout.showLoading()
        progress_layout.postDelayed({
            progress_layout.showEmpty(View.OnClickListener {
                progress_layout.showContent()
            })
        }, 800)

    }
}
