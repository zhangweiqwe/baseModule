package cn.wsgwz.myapplication

import android.os.Bundle
import cn.wsgwz.common.ToolbarManager
import kotlinx.android.synthetic.main.activity_test_progress_layout.*

class TestProgressLayoutActivity : AppBaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomContentView(R.layout.activity_test_progress_layout)

        toolbar.title("测试").isGoBack(true)

        progressLayout.apply {
            showLoading()
            other_text.postDelayed({
                showEmpty {
                    showError {
                        showContent()
                    }
                }

            }, 1200)

        }

    }
}
