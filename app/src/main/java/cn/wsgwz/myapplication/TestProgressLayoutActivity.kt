package cn.wsgwz.myapplication

import android.os.Bundle
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressConstraintLayout
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_test_progress_layout.*

class TestProgressLayoutActivity : AppBaseActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_progress_layout)



        progress_layout.apply {
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
