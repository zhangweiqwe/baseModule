package cn.wsgwz.baselibrary.widgets.suspension

import cn.wsgwz.basemodule.BaseApplication


object SuspensionWindowFactory {

    fun create(suspensionWindowType: SuspensionWindowType): SuspensionWindow {
        return when (suspensionWindowType) {
            SuspensionWindowType.LOG -> {
                LogSuspensionWindow.create(BaseApplication.getInstance())
            }
        }
    }
}