package cn.wsgwz.basemodule.widgets.dialog

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.R
import java.lang.Exception


abstract class BaseSearchDialogFragment : DialogFragment() {

    //abstract fun getSearchFragment(): Fragment


    /*override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_search_dialog_fragment, container, false)
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* sv = view.findViewById(R.id.sv)
        sv.requestFocus()
        sv.apply {
            val fragment = this@BaseSearchDialogFragment.fragment
            if (fragment is SearchView.OnQueryTextListener) {
                setOnQueryTextListener(fragment)
            } else {
                throw Exception("non support")
            }
            *//*try {
                val tv = findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
                tv.setTextColor(color)
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
                tv.setHintTextColor()
            } catch (e: Exception) {
                e.printStackTrace()
            }*//*
            queryHint = this@BaseSearchDialogFragment.queryHint
        }*/

        //childFragmentManager.beginTransaction().replace(R.id.fl, getSearchFragment()).commit()

    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.Theme_SearchDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    val decorViewClazz = Class.forName("com.android.internal.policy.DecorView")
                    val field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor")
                    field.isAccessible = true
                    field.setInt(window.decorView, Color.TRANSPARENT)  //改为透明
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }
    }


}