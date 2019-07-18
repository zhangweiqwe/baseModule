package cn.wsgwz.basemodule.widgets.progressActivity

import android.content.Context
import android.view.View
import android.view.ViewGroup

interface LayoutDelegate {

    fun getStateViews():Array<View?>

    fun initView(currentState: Int, context: Context, parentView: ViewGroup, description: CharSequence? = null, buttonClickListener: View.OnClickListener? = null, skipIds: List<Int>? = null)

}