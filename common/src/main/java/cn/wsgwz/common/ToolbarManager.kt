package cn.wsgwz.common

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.view.ViewStub
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.ToolbarUtil

class ToolbarManager private constructor() {
    companion object {

        @Volatile
        private var instance: ToolbarManager? = null


        @JvmStatic
        fun get() = instance ?: synchronized(this) {
            instance ?: ToolbarManager().also { instance = it }
        }
    }


    fun into(appCompatActivity: AppCompatActivity): Toolbar {

        ToolbarUtil.setCustomView(appCompatActivity, R.layout.custom_toolbar)
        return Toolbar(
                appCompatActivity.findViewById(R.id.toolbar_parent_cl),
                appCompatActivity,
                appCompatActivity
        )
    }


    fun into(toolbarView: View): Toolbar {
        ToolbarUtil.setCustomView(toolbarView.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar), R.layout.custom_toolbar)
        return Toolbar(toolbarView)

    }

    class Toolbar(
            private val toolbarView: View,
            private val context: Context = toolbarView.context,
            appCompatActivity: AppCompatActivity? = null
    ) {


        init {
            appCompatActivity?.also { appCompatActivity ->
                toolbarView.apply {
                    if (findViewById<View>(R.id.toolbar_back_cl) == null) {
                        findViewById<ViewStub>(R.id.toolbar_back_vs).inflate()
                    }
                    findViewById<ConstraintLayout>(R.id.toolbar_back_cl).apply {
                        setOnClickListener { appCompatActivity.finish() }
                    }
                }
            }


        }

        fun title(text: CharSequence?): Toolbar {
            toolbarView.apply {
                if (findViewById<TextView>(R.id.toolbar_title_tv) == null) {
                    findViewById<ViewStub>(R.id.toolbar_title_vs).inflate()
                }
                findViewById<TextView>(R.id.toolbar_title_tv).text = text
            }
            return this
        }

        fun title(@StringRes resId: Int) = title(context.getString(resId))


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

        fun addMenu(view: View, params: LinearLayout.LayoutParams): Toolbar {
            toolbarView.apply {
                if (findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll) == null) {
                    findViewById<ViewStub>(R.id.toolbar_right_multiple_menu_vs).inflate()

                    findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll).apply {
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

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.foreground = context.obtainStyledAttributes(intArrayOf(android.R.attr.selectableItemBackgroundBorderless)).getDrawable(0)
            }*/

            return this
        }


        fun removeMenu(view: View): Toolbar {
            toolbarView.findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll)?.removeView(view)
            return this
        }
    }
}