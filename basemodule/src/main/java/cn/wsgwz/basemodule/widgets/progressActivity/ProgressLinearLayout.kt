package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_CONTENT
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_EMPTY
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_ERROR
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_LOADING
import kotlinx.android.synthetic.main.view_progress_layout_empty.view.*
import kotlinx.android.synthetic.main.view_progress_layout_error.view.*
import java.util.ArrayList

class ProgressLinearLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ProgressLayout {
    companion object {
        private const val TAG = "ProgressLinearLayout"
    }


    private var mCurrentState = STATE_CONTENT
    private val contentViews = ArrayList<View>()

    private var loadingState: View? = null
    private var emptyState: View? = null
    private var errorState: View? = null


    override fun getCurrentState() = mCurrentState

    private val commonSkipIds = ArrayList<Int>().apply {
        add(R.id.toolbar)
    }

    override fun showContent() {
        switchState(STATE_CONTENT, null, null, null)
    }

    override fun showLoading() {
        switchState(STATE_LOADING, null, null, null)
    }

    override fun showEmpty(config: ProgressLayout.Config) {
        switchState(STATE_EMPTY, config.description, config.buttonClickListener, config.skipIds)
    }

    override fun showError(config: ProgressLayout.Config) {
        switchState(STATE_ERROR, config.description, config.buttonClickListener, config.skipIds)
    }


    private fun hideAllStates() {
        for (v in arrayOf(loadingState, emptyState, errorState)) {
            v?.visibility = View.GONE
        }
    }


    private fun setContentVisibility(visible: Boolean, skipIds: ArrayList<Int>?) {


        for (v in contentViews) {
            if (skipIds == null) {
                if (!commonSkipIds.contains(v.id)) {
                    v.visibility = if (visible) View.VISIBLE else View.GONE
                }
            } else {
                if (!skipIds.contains(v.id) && !commonSkipIds.contains(v.id)) {
                    v.visibility = if (visible) View.VISIBLE else View.GONE
                }
            }
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)

        for (v in arrayOf(loadingState, emptyState, errorState)) {
            if (v == child) {
                return
            }
        }
        contentViews.add(child)


    }

    private fun switchState(currentState: Int, description: CharSequence? = null, buttonClickListener: OnClickListener? = null, skipIds: ArrayList<Int>? = null) {
        mCurrentState = currentState
        hideAllStates()
        when (currentState) {
            STATE_CONTENT -> {
                setContentVisibility(true, skipIds)
            }
            STATE_LOADING -> {
                setContentVisibility(false, skipIds)
                if (loadingState == null) {
                    loadingState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_loading, null)
                    addView(loadingState, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                } else {
                    loadingState?.visibility = View.VISIBLE
                }
            }
            STATE_EMPTY -> {
                setContentVisibility(false, skipIds)
                if (emptyState == null) {
                    emptyState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_empty, null)
                    addView(emptyState, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                } else {
                    emptyState?.visibility = View.VISIBLE
                }
                emptyState?.apply {
                    empty_state_text.text = description
                    setOnClickListener(buttonClickListener)
                }
            }
            STATE_ERROR -> {
                setContentVisibility(false, skipIds)
                if (errorState == null) {
                    errorState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_error, null)
                    addView(errorState, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

                    errorState?.apply {
                        error_state_text.text = description
                        error_state_bn.setOnClickListener(buttonClickListener)
                    }
                } else {
                    errorState?.visibility = View.VISIBLE
                }
            }
        }
    }


}