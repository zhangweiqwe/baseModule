package cn.wsgwz.basemodule.interfaces

import androidx.annotation.StringRes

interface BaseWindowInterface {
    fun toast(charSequence: CharSequence?)

    fun toast(@StringRes resId: Int)


    /*fun log(any: Any?){
        Logger.t(this::class.java.simpleName).d(any)
    }*/
}