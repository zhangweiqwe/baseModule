package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_CONTENT
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_EMPTY
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_ERROR
import cn.wsgwz.basemodule.widgets.progressActivity.ProgressLayout.Companion.STATE_LOADING
import kotlinx.android.synthetic.main.activity_base_network.view.*
import java.util.ArrayList

open class ProgressConstraintLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ProgressLayout {
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

    companion object {


        private const val TAG = "ProgressConstraintLayout"
        private var layoutDelegate: LayoutDelegate? = null


        @JvmStatic
        fun setDefaultLayoutDelegate(layoutDelegate: LayoutDelegate) {
            ProgressConstraintLayout.layoutDelegate = layoutDelegate
        }
    }

    private var mCurrentState = STATE_CONTENT
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


    override fun getCurrentState() = mCurrentState

    private fun hideAllStates() {
        for ((index, v) in layoutDelegate.getStateViews().withIndex()) {
            LLog.d(TAG, "hideAllStates ${layoutDelegate.getStateViews()} ${layoutDelegate.getStateViews().size}  ${v?.hashCode()}")
            v?.visibility = View.GONE
        }
    }

    private fun setContentVisibility(visible: Boolean, skipIds: ArrayList<Int>?) {

        val mSkipIds = ArrayList<Int>().apply {
            add(R.id.toolbar_stub)
        }
        skipIds?.also {
            mSkipIds.addAll(it)
        }

        for (v in contentViews) {

            LLog.d(TAG, "setContentVisibility ${v?.hashCode()}")


            if (!mSkipIds.contains(v.id)) {
                v.visibility = if (visible) View.VISIBLE else View.GONE
            } else {
                LLog.d(TAG, "setContentVisibility skipIds ${v.id}")
            }
            /*if (skipIds == null) {
                v.visibility = if (visible) View.VISIBLE else View.GONE
            } else {
                if (!skipIds.contains(v.id)) {
                    v.visibility = if (visible) View.VISIBLE else View.GONE
                } else {
                    LLog.d(TAG, "setContentVisibility skipIds ${v.id}")
                }
            }*/


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

    private fun switchState(currentState: Int, description: CharSequence? = null, buttonClickListener: OnClickListener? = null, skipIds: ArrayList<Int>? = null) {
        mCurrentState = currentState
        hideAllStates()
        when (currentState) {
            STATE_CONTENT -> {
                setContentVisibility(true, skipIds)
            }
            STATE_LOADING -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
            STATE_EMPTY -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
            STATE_ERROR -> {
                setContentVisibility(false, skipIds)
                layoutDelegate.initView(currentState, context, this, description, buttonClickListener, skipIds)
            }
        }
    }


}