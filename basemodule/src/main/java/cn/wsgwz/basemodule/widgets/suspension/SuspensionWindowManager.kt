package cn.wsgwz.baselibrary.widgets.suspension


import android.content.Context
import android.content.Intent
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.widgets.suspension.SuspensionWindowActivity

private const val TAG = "SuspensionWindowManager"

class SuspensionWindowManager private constructor() {


    private val suspensionWindowMap = HashMap<SuspensionWindowType, SuspensionWindow>()

    fun show(suspensionWindowType: SuspensionWindowType) {
        if (suspensionWindowMap[SuspensionWindowType.LOG] == null) {
            suspensionWindowMap[SuspensionWindowType.LOG] = SuspensionWindowFactory.create(suspensionWindowType)
        }

        suspensionWindowMap[SuspensionWindowType.LOG]?.show()
    }

    fun hide(suspensionWindowType: SuspensionWindowType) {
        suspensionWindowMap[suspensionWindowType]?.hide()
        when (suspensionWindowType) {
            SuspensionWindowType.LOG -> {
                suspensionWindowMap.remove(suspensionWindowType)
            }
        }
    }



    fun showWithRequestPermission(context: Context, suspensionWindowType: SuspensionWindowType) {
        context.startActivity(Intent(context, SuspensionWindowActivity::class.java).apply {
            putExtra("suspensionWindowType", suspensionWindowType)
        })
    }

    companion object {


        // For Singleton instantiation
        @Volatile
        private var instance: SuspensionWindowManager? = null


        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: SuspensionWindowManager().also { instance = it }
            }


        @JvmStatic
        fun init(context: Context) {
            if (BaseApplication.getPreferences().getBoolean("is_show_network_data_suspension_window", false)) {
                getInstance().showWithRequestPermission(context, SuspensionWindowType.LOG)
            }
        }
    }
}