package cn.wsgwz.basemodule.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView

class ScrollWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = android.R.attr.webViewStyle
) : WebView(context, attrs, defStyleAttr) {

    private var mOnScrollChangeListener:OnScrollChangeListener? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mOnScrollChangeListener?.onScrollChange(this,l,t,oldl,oldt)
    }

    fun setOnScrollChangeListener(onScrollChangeListener: OnScrollChangeListener) {
        mOnScrollChangeListener = onScrollChangeListener
    }
    interface OnScrollChangeListener {
        /**
         * Called when the scroll position of a view changes.
         *
         * @param v The view whose scroll position has changed.
         * @param scrollX Current horizontal scroll origin.
         * @param scrollY Current vertical scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         * @param oldScrollY Previous vertical scroll origin.
         */
        fun onScrollChange(v: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int)
    }
}