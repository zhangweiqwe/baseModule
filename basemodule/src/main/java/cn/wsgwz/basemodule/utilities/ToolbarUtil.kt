package cn.wsgwz.basemodule.utilities

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.wsgwz.basemodule.R

object ToolbarUtil {
    /* fun setCustomView(appCompatActivity: AppCompatActivity, @LayoutRes resId: Int): View {
         appCompatActivity.setSupportActionBar(appCompatActivity.findViewById(R.id.toolbar))
         return ToolbarUtil.setCustomView(appCompatActivity.supportActionBar,appCompatActivity,  resId)
     }

     fun setCustomView( actionBar: ActionBar?,context: Context, @LayoutRes resId: Int): View {
         return LayoutInflater.from(context).inflate(resId, null).apply {
             ToolbarUtil.setCustomView(actionBar, this)
         }
     }

     fun setCustomView(actionBar: ActionBar?, view: View):View {
         actionBar?.also {
             it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
             it.setHomeButtonEnabled(false)
             it.setDisplayShowTitleEnabled(false)
             it.setDisplayShowCustomEnabled(true)
             it.setCustomView(
                 view,
                 ActionBar.LayoutParams(
                     ActionBar.LayoutParams.MATCH_PARENT,
                     ActionBar.LayoutParams.MATCH_PARENT,
                     Gravity.CENTER
                 )
             )
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                 (view.parent as Toolbar).also { toolbar ->
                     toolbar.setContentInsetsAbsolute(0, 0)
                 }
             }
         }
         return view
     }*/

    fun setCustomView(appCompatActivity: AppCompatActivity, @LayoutRes resId: Int): View {
        val toolbar = appCompatActivity.findViewById<Toolbar>(R.id.toolbar)
        appCompatActivity.setSupportActionBar(toolbar)
        return ToolbarUtil.setCustomView(toolbar, appCompatActivity, resId)
    }

    fun setCustomView(toolbar: Toolbar, context: Context, @LayoutRes resId: Int): View {
        return LayoutInflater.from(context).inflate(resId, null).apply {
            ToolbarUtil.setCustomView(toolbar, this)
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