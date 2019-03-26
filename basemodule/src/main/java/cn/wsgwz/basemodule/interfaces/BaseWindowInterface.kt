package cn.wsgwz.basemodule.interfaces

import androidx.annotation.StringRes
import com.orhanobut.logger.Logger

interface BaseWindowInterface {
    fun toast(charSequence: CharSequence?)

    fun toast(@StringRes resId: Int)

    fun login() {}

    /*fun log(any: Any?){
        Logger.t(this::class.java.simpleName).d(any)
    }*/
}