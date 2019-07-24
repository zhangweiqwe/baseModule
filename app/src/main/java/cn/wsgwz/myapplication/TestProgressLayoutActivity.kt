package cn.wsgwz.myapplication

import android.os.Bundle
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressConstraintLayout
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout
import kotlinx.android.synthetic.main.activity_test_progress_layout.*

class TestProgressLayoutActivity : AppBaseActivity() {

    override val progressLayout: ProgressLayout  by lazy {
        findViewById<ProgressConstraintLayout>(R.id.progress_layout_2)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomContentView(R.layout.activity_test_progress_layout)


        progressLayout.apply {
            showLoading()
            other_text.postDelayed({
                showEmpty {
                    showError {
                        showContent()
                    }
                }

            }, 2000)

        }

    }
}
