package cn.wsgwz.baselibrary.widgets.suspension


import android.content.Context
import cn.wsgwz.basemodule.utilities.InjectorUtils

abstract class SuspensionWindow(context: Context) {
    protected val suspensionWindowRepository = InjectorUtils.provideSuspensionWindowRepository(context)
    abstract fun show()
    abstract fun hide()

}