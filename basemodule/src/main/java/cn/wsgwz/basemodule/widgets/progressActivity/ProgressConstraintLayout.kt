package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.utilities.LLog
import java.util.ArrayList

open class ProgressConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ProgressLayout {
    companion object {
        private const val TAG = "ProgressConstraintLayout"
        private var layoutDelegate: LayoutDelegate? = null


        @JvmStatic
        fun setDefaultLayoutDelegate(layoutDelegate: LayoutDelegate) {
            ProgressConstraintLayout.layoutDelegate = layoutDelegate
        }
    }

    private var mCurrentState = ProgressLayout.CurrentState.CONTENT
    private val contentViews = ArrayList<View>()
    private var layoutDelegate: LayoutDelegate

    init {
        layoutDelegate = ProgressConstraintLayout.layoutDelegate.let {
            it ?: LayoutDelegateImpl()
        }
    }


    override fun getLayoutDelegate(): LayoutDelegate = layoutDelegate

    override fun setLayoutDelegate(layoutDelegate: LayoutDelegate) {
        for ((index, v) in this.layoutDelegate.getStateViews().withIndex()) {
            v?.also {
                throw Exception("non support getStateViews() must all null")
            }
        }
        this.layoutDelegate = layoutDelegate
    }


    override fun showContent() {
        switchState(ProgressLayout.CurrentState.CONTENT, null, null, null)
    }

    override fun showContent(skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.CONTENT, null, null, skipIds)
    }

    override fun showLoading() {
        switchState(ProgressLayout.CurrentState.LOADING, null, null, null)
    }

    override fun showLoading(skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.LOADING, null, null, skipIds)
    }

    override fun showEmpty() {
        switchState(ProgressLayout.CurrentState.EMPTY, null, null, null)
    }

    override fun showEmpty(skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.EMPTY, null, null, skipIds)
    }

    override fun showEmpty(buttonClickListener: OnClickListener) {
        switchState(ProgressLayout.CurrentState.EMPTY, null, buttonClickListener, null)
    }

    override fun showEmpty(buttonClickListener: OnClickListener, skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.EMPTY, null, buttonClickListener, skipIds)
    }

    override fun showEmpty(description: CharSequence?, buttonClickListener: OnClickListener, skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.EMPTY, description, buttonClickListener, skipIds)
    }

    override fun showError() {
        switchState(ProgressLayout.CurrentState.ERROR, null, null, null)
    }

    override fun showError(skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.ERROR, null, null, skipIds)
    }

    override fun showError(buttonClickListener: OnClickListener) {
        switchState(ProgressLayout.CurrentState.ERROR, null, buttonClickListener, null)

    }

    override fun showError(buttonClickListener: OnClickListener, skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.ERROR, null, buttonClickListener, skipIds)

    }

    override fun showError(description: CharSequence?, buttonClickListener: OnClickListener, skipIds: List<Int>) {
        switchState(ProgressLayout.CurrentState.ERROR, description, buttonClickListener, skipIds)
    }

    override fun getCurrentState(): ProgressLayout.CurrentState = mCurrentState

    override fun isContentCurrentState(): Boolean = mCurrentState == ProgressLayout.CurrentState.CONTENT

    override fun isLoadingCurrentState(): Boolean = mCurrentState == ProgressLayout.CurrentState.LOADING

    override fun isEmptyCurrentState(): Boolean = mCurrentState == ProgressLayout.CurrentState.EMPTY

    override fun isErrorCurrentState(): Boolean = mCurrentState == ProgressLayout.CurrentState.ERROR


    private fun hideAllStates() {
        for ((index, v) in layoutDelegate.getStateViews().withIndex()) {
            LLog.d(TAG, "hideAllStates ${layoutDelegate.getStateViews()} ${layoutDelegate.getStateViews().size}  ${v?.hashCode()}")
            v?.visibility = View.GONE
        }
    }

    private fun setContentVisibility(visible: Boolean, skipIds: List<Int>?) {
        for (v in contentViews) {

            LLog.d(TAG, "setContentVisibility ${v?.hashCode()}")
            if (skipIds == null) {
                v.visibility = if (visible) View.VISIBLE else View.GONE
            } else {
                if (!skipIds.contains(v.id)) {
                    v.visibility = if (visible) View.VISIBLE else View.GONE
                } else {
                    LLog.d(TAG, "setContentVisibility skipIds ${v.id}")
                }
            }
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        super.addView(child, index, params)

        for ((index, v) in layoutDelegate.getStateViews().withIndex()) {
            LLog.d(TAG, "addView(child: View, index: Int, params: ViewGroup.LayoutParams?) --> ${child.hashCode()} ${v?.hashCode()} $index")
            if (v == child) {
                LLog.d(TAG, "addView(child: View, index: Int, params: ViewGroup.LayoutParams?) child== i ${child.id}")
                return
            }
        }
        contentViews.add(child)


    }

    private fun switchState(currentState: ProgressLayout.CurrentState, description: CharSequence? = null, buttonClickListener: OnClickListener? = null, skipIds: List<Int>? = null) {
        mCurrentState = currentState
        hideAllStates()
        when (currentState) {
            ProgressLayout.CurrentState.CONTENT -> {
                setContentVisibility(true, skipIds)
            }
            ProgressLayout.CurrentState.LOADING -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
            ProgressLayout.CurrentState.EMPTY -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
            ProgressLayout.CurrentState.ERROR -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
        }
    }


}