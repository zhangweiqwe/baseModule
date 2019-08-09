package cn.wsgwz.basemodule.widgets.progressActivity

import android.view.View
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.R

interface ProgressLayout {


    companion object {
        private const val TAG = "ProgressLayout"

        const val STATE_CONTENT = 1000
        const val STATE_LOADING = 1001
        const val STATE_EMPTY = 1002
        const val STATE_ERROR = 1003
    }





    fun showContent()

    fun showLoading()

    fun showEmpty(config: Config)
    fun showEmpty(l: (View) -> Unit) = showEmpty(Config.Builder().buttonClickListener(View.OnClickListener {
        l(it)
    }).description(BaseApplication.getInstance().getString(R.string.progress_layout_empty_description)).build())

    fun showError(config: Config)
    fun showError(l: (View) -> Unit) = showError(Config.Builder().buttonClickListener(View.OnClickListener {
        l(it)
    }).description(BaseApplication.getInstance().getString(R.string.progress_layout_error_description)).build())

    fun getCurrentState(): Int


    fun isContentCurrentState() = getCurrentState() == STATE_CONTENT
    fun isLoadingCurrentState() = getCurrentState() == STATE_LOADING
    fun isEmptyCurrentState() = getCurrentState() == STATE_EMPTY
    fun isErrorCurrentState() = getCurrentState() == STATE_ERROR




}