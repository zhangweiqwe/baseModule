package cn.wsgwz.basemodule.interfaces

import androidx.annotation.StringRes
import cn.wsgwz.basemodule.utilities.LLog

interface BaseWindowInterface {
    fun toast(charSequence: CharSequence?)

    fun toast(@StringRes resId: Int)

    fun log(msg: String?) {
        LLog.d(this::class.java.simpleName, msg)
    }


    /*fun log(any: Any?){
        Logger.t(this::class.java.simpleName).d(any)
    }*/
}