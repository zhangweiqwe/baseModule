package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.utilities.LLog
import kotlinx.android.synthetic.main.view_progress_layout_empty.view.*
import kotlinx.android.synthetic.main.view_progress_layout_error.view.*

internal class LayoutDelegateImpl : LayoutDelegate {
    companion object {
        private const val TAG = "LayoutDelegateImpl"
    }

    init {
        LLog.d(TAG, "init")
    }

    private var loadingState: View? = null
    private var emptyState: View? = null
    private var errorState: View? = null

    private fun inflateLoadingView(context: Context, parentView: ViewGroup) {
        if (loadingState == null) {
            loadingState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_loading, null)
            parentView.addView(loadingState, getLayoutParams())

        } else {
            loadingState?.visibility = View.VISIBLE
        }
        LLog.d(TAG, "inflateLoadingView $loadingState $emptyState $errorState")
    }

    private fun inflateEmptyView(context: Context, parentView: ViewGroup) {
        if (emptyState == null) {
            emptyState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_empty, null)
            parentView.addView(emptyState, getLayoutParams())
        } else {
            emptyState?.visibility = View.VISIBLE
        }
        LLog.d(TAG, "inflateEmptyView $loadingState $emptyState $errorState")
    }

    private fun inflateErrorView(context: Context, parentView: ViewGroup) {
        if (errorState == null) {
            errorState = LayoutInflater.from(context).inflate(R.layout.view_progress_layout_error, null)
            parentView.addView(errorState, getLayoutParams())
        } else {
            errorState?.visibility = View.VISIBLE
        }
        LLog.d(TAG, "inflateErrorView $loadingState $emptyState $errorState")
    }


    override fun getStateViews(): Array<View?> = arrayOf(loadingState, emptyState, errorState)

    override fun initView(currentState: Int, context: Context, parentView: ViewGroup, description: CharSequence?, buttonClickListener: View.OnClickListener?, skipIds: List<Int>?) {

        LLog.d(TAG, currentState.toString())

        when (currentState) {
            ProgressLayout.STATE_LOADING -> {
                inflateLoadingView(context, parentView)
            }
            ProgressLayout.STATE_EMPTY -> {
                inflateEmptyView(context, parentView)
                emptyState?.apply {
                    empty_state_text.text = description
                    empty_state_bn.setOnClickListener(buttonClickListener)
                }
            }
            ProgressLayout.STATE_ERROR -> {
                inflateErrorView(context, parentView)
                errorState?.apply {
                    error_state_text.text = description
                    error_state_bn.setOnClickListener(buttonClickListener)
                }
            }
        }
    }


    private fun getLayoutParams() = ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT).apply {

        topToTop = ConstraintSet.PARENT_ID
        bottomToBottom = ConstraintSet.PARENT_ID
        startToStart = ConstraintSet.PARENT_ID
        endToEnd = ConstraintSet.PARENT_ID
    }


}