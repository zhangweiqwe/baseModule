package cn.wsgwz.common

import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.wsgwz.basemodule.utilities.ToolbarUtil

class ToolbarManager private constructor() {
    companion object {



        @JvmStatic
        fun with(appCompatActivity: AppCompatActivity) = with(appCompatActivity, false)

        @JvmStatic
        fun with(appCompatActivity: AppCompatActivity, isGoBack: Boolean): MyToolbar {
            val toolbarView = ToolbarUtil.setCustomView(appCompatActivity, R.layout.custom_toolbar)
            return MyToolbar(toolbarView).apply {
                isGoBack(isGoBack)
            }
        }


        @JvmStatic
        fun with(toolbarView: View): MyToolbar {
            ToolbarUtil.setCustomView(toolbarView.findViewById<Toolbar>(R.id.toolbar), R.layout.custom_toolbar)
            return MyToolbar(toolbarView)
        }

        @JvmStatic
        fun with(toolbar: Toolbar): MyToolbar {
            val view = ToolbarUtil.setCustomView(toolbar, R.layout.custom_toolbar)
            return MyToolbar(view)
        }



        /*@Volatile
        private var instance: ToolbarManager? = null


        @JvmStatic
        private fun get() = instance ?: synchronized(this) {
            instance ?: ToolbarManager().also { instance = it }
        }*/
    }


}