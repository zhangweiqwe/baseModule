package cn.wsgwz.myapplication.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat

class ToolbarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {



    val backIV = ImageView(context)


    init {

        /*addView()
        ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)*/
    }


    companion object {
        const val BACK_TITLE_STYLE = 1000
        const val BACK_TITLE_SINGLE_MENU_STYLE = 1001
        const val BACK_TITLE_SINGLE_MULTIPLE_MENU_STYLE = 1002

        fun with(appCompatActivity: AppCompatActivity) {
        }

    }

    interface Toolbar{

    }


}