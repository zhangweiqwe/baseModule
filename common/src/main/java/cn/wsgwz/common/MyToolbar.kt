package cn.wsgwz.common

import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.os.Build
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.BaseToolbar
import kotlinx.android.synthetic.main.custom_toolbar.view.*
import kotlinx.android.synthetic.main.toolbar_center_title_item.view.*
import kotlinx.android.synthetic.main.toolbar_left_back_item.view.*
import kotlinx.android.synthetic.main.toolbar_right_multiple_menu_item.view.*

class MyToolbar(toolbarView: View) : BaseToolbar(toolbarView) {


    private val finishOnClickListener by lazy {
        View.OnClickListener {
            if (context is Activity) {
                context.finish()
            }
        }
    }

    override fun title(text: CharSequence?): MyToolbar {
        toolbarView.apply {
            if (toolbar_title_tv == null) {
                toolbar_title_vs.inflate()
            }
            toolbar_title_tv.text = text
        }
        return this
    }

    override fun isGoBack(isGoBack: Boolean): MyToolbar {
        if (isGoBack) {
            toolbarView.apply {
                if (toolbar_back_iv == null) {
                    toolbar_back_vs.inflate()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        toolbar_back_iv.background = context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackgroundBorderless)).getDrawable(0)
                    }
                }
                toolbar_back_iv.setOnClickListener(finishOnClickListener)
            }
        } else {
            toolbarView.apply {
                if (toolbar_back_iv != null) {
                    toolbar_back_iv.visibility = View.GONE
                }
            }
        }
        return this
    }


    fun addMenu(view: View) = addMenu(
            view,
            when (view) {
                is TextView -> {
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                }
                else -> {
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)
                }
            }
    )

    fun addMenu(view: View, params: LinearLayout.LayoutParams): MyToolbar {
        toolbarView.apply {
            if (toolbar_right_multiple_menu_ll == null) {
                toolbar_right_multiple_menu_vs.inflate()

                toolbar_right_multiple_menu_ll.apply {
                    showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                    dividerDrawable = ShapeDrawable(RoundRectShape(FloatArray(8), null, null)).apply {
                        paint.color = Color.TRANSPARENT
                        paint.style = Paint.Style.STROKE
                        intrinsicWidth = DensityUtil.dp2px(10f).toInt()
                    }
                }

            }
            findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll).addView(view, params)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.background = context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackgroundBorderless)).getDrawable(0)
        }

        return this
    }
}