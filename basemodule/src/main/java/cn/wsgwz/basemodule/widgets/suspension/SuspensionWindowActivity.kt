package cn.wsgwz.basemodule.widgets.suspension


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.KeyEvent
import cn.wsgwz.baselibrary.widgets.suspension.SuspensionWindowManager
import cn.wsgwz.baselibrary.widgets.suspension.SuspensionWindowType
import cn.wsgwz.basemodule.BaseActivity
import cn.wsgwz.basemodule.utilities.WindowUtil

private const val REQUEST_CODE_OVERLAY_PERMISSION = 1000

class SuspensionWindowActivity : BaseActivity() {

    private val suspensionWindowManager = SuspensionWindowManager.getInstance()
    private lateinit var suspensionWindowType: SuspensionWindowType

    override fun onCreate(savedInstanceState: Bundle?) {
        //WindowUtil.setStatusBarTransparent(this)
        super.onCreate(savedInstanceState)

        suspensionWindowType = intent.getSerializableExtra("suspensionWindowType") as SuspensionWindowType
        //suspensionWindowType = intent.getParcelableExtra("suspensionWindowType")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                suspensionWindowManager.show(suspensionWindowType)
                finish()
            } else {
                startActivityForResult(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:$packageName")
                    ), REQUEST_CODE_OVERLAY_PERMISSION
                )
            }
        } else {
            suspensionWindowManager.show(suspensionWindowType)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Handler().postDelayed({
                if (Settings.canDrawOverlays(this)) {
                    suspensionWindowManager.show(suspensionWindowType)
                }
                finish()
            }, 400)
        } else {
            finish()
        }
    }

    //https://github.com/zhangweiqwe/DiDiHelper/blob/master/app/src/main/java/cn/zr/util/LLog.kt
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) true else super.onKeyDown(keyCode, event)
    }

}