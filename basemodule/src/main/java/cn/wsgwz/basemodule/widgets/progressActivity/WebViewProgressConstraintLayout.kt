package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_CONTENT
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_EMPTY
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_ERROR
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_LOADING
import kotlinx.android.synthetic.main.view_progress_layout_empty.view.*
import kotlinx.android.synthetic.main.view_progress_layout_error.view.*
import java.util.ArrayList

internal class WebViewProgressConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : BaseProgressConstraintLayout(context, attrs, defStyleAttr), ProgressLayout {
    val videoContainerId = 1002454362

    override val commonSkipIds: ArrayList<Int> = ArrayList<Int>().apply {
        add(R.id.toolbar_parent_layout)
        add(videoContainerId)
    }

    override val loadingLayoutId: Int = R.layout.view_progress_layout_loading
    override val emptyLayoutId: Int = R.layout.view_progress_layout_empty
    override val errorLayoutId: Int = R.layout.view_progress_layout_error

    override fun initEmptyView(view: View, description: CharSequence?, buttonClickListener: OnClickListener?) {
        view.apply {
            empty_state_text.text = description
            setOnClickListener(buttonClickListener)
        }
    }

    override fun initErrorView(view: View, description: CharSequence?, buttonClickListener: OnClickListener?) {
        view?.apply {
            error_state_text.text = description
            error_state_bn.setOnClickListener(buttonClickListener)
        }
    }

}