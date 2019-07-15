package cn.wsgwz.basemodule.widgets.progressActivity

import android.view.View

interface ProgressLayout {
    enum class CurrentState {
        CONTENT, LOADING, EMPTY, ERROR
    }

    fun showContent()
    fun showContent(skipIds: List<Int>)


    fun showLoading()
    fun showLoading(skipIds: List<Int>)


    fun showEmpty()
    fun showEmpty(skipIds: List<Int>)
    fun showEmpty(buttonClickListener: View.OnClickListener)
    fun showEmpty(buttonClickListener: View.OnClickListener, skipIds: List<Int>)
    fun showEmpty(description: CharSequence?, buttonClickListener: View.OnClickListener, skipIds: List<Int>)


    fun showError()
    fun showError(skipIds: List<Int>)
    fun showError(buttonClickListener: View.OnClickListener)
    fun showError(buttonClickListener: View.OnClickListener, skipIds: List<Int>)
    fun showError(description: CharSequence?, buttonClickListener: View.OnClickListener, skipIds: List<Int>)


    fun getCurrentState(): CurrentState

    fun isContentCurrentState(): Boolean
    fun isLoadingCurrentState(): Boolean
    fun isEmptyCurrentState(): Boolean
    fun isErrorCurrentState(): Boolean


    fun setLayoutDelegate(layoutDelegate: LayoutDelegate)
    fun getLayoutDelegate(): LayoutDelegate


}