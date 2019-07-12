package cn.wsgwz.basemodule.utilities

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.wsgwz.basemodule.R

object ToolbarUtil {

    fun setCustomView(appCompatActivity: AppCompatActivity, @LayoutRes resId: Int): View {
        val toolbar = appCompatActivity.findViewById<Toolbar>(R.id.toolbar)
        appCompatActivity.setSupportActionBar(toolbar)
        return setCustomView(toolbar, resId)
    }

    fun setCustomView(toolbar: Toolbar,  @LayoutRes resId: Int): View {
        return LayoutInflater.from(toolbar.context).inflate(resId, null).apply {
            setCustomView(toolbar, this)
        }
    }
    fun setCustomView(toolbar: Toolbar, view: View): View {
        toolbar.addView(
            view,
            Toolbar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            (view.parent as Toolbar).also { toolbar ->
                toolbar.setContentInsetsAbsolute(0, 0)
            }
        }
        return view
    }


}