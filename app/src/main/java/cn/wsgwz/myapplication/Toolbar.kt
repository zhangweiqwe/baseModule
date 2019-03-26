package cn.wsgwz.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.view.ViewStub
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import cn.wsgwz.basemodule.utilities.DensityUtil
import cn.wsgwz.basemodule.utilities.ToolbarUtil

class Toolbar private constructor() {
    companion object {

        @Volatile
        private var instance: Toolbar? = null


        @JvmStatic
        fun get() = instance ?: synchronized(this) {
            instance ?: Toolbar().also { instance = it }
        }
    }


    fun into(appCompatActivity: AppCompatActivity): Builder {

        ToolbarUtil.setCustomView(appCompatActivity, R.layout.toolbar)
        return Builder(
            appCompatActivity.findViewById(R.id.toolbar_parent_cl),
            appCompatActivity,
            appCompatActivity
        )
    }


    fun into(toolbarView: View) = Builder(toolbarView)

    class Builder(
        private val toolbarView: View,
        private val context: Context = toolbarView.context,
        private val appCompatActivity: AppCompatActivity? = null
    ) {


        init {
            appCompatActivity?.also { appCompatActivity ->
                toolbarView.apply {
                    if (findViewById<ImageView>(R.id.toolbar_back_iv) == null) {
                        findViewById<ViewStub>(R.id.toolbar_back_vs).inflate()
                    }
                    findViewById<ImageView>(R.id.toolbar_back_iv).setOnClickListener { appCompatActivity.finish() }
                }
            }


        }

        fun title(text: CharSequence): Builder {
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
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        )

        fun addMenu(view: View, params: LinearLayout.LayoutParams): Builder {
            toolbarView.apply {
                if (findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll) == null) {
                    findViewById<ViewStub>(R.id.toolbar_right_multiple_menu_vs).inflate()

                    findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll).apply {
                        showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                        dividerDrawable = ShapeDrawable(RoundRectShape(FloatArray(8), null, null)).apply {
                            paint.color = Color.TRANSPARENT
                            paint.style = Paint.Style.STROKE
                            intrinsicWidth = DensityUtil.dp2px(context, 8f).toInt()
                        }
                    }

                }
                findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll).addView(view, params)
            }
            return this
        }

        fun removeMenu(view: View): Builder {
            toolbarView.findViewById<LinearLayout>(R.id.toolbar_right_multiple_menu_ll)?.removeView(view)
            return this
        }
    }
}