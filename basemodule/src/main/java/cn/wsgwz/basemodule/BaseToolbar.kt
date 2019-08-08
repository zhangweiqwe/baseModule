package cn.wsgwz.basemodule

import android.content.Context
import android.view.View
import androidx.annotation.StringRes

abstract class BaseToolbar(val toolbarView: View) {


    val context: Context = toolbarView.context
    abstract fun title(text: CharSequence?): BaseToolbar

    fun title(@StringRes resId: Int) = title(context.getString(resId))

    abstract fun isGoBack(isGoBack: Boolean): BaseToolbar

}