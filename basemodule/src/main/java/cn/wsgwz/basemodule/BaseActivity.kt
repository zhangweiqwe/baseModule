package cn.wsgwz.basemodule

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import cn.wsgwz.basemodule.utilities.WindowUtil


open class BaseActivity : AppCompatActivity(), BaseWindowInterface {
    private var loadingDialogFragment: LoadingDialogFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BaseApplication.addActivity(this)
        WindowUtil.setStatusBarTransparent(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BaseApplication.removeActivity(this)
    }


    override fun toast(charSequence: CharSequence?) {
        Toast.makeText(this, charSequence, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        toast(getString(resId))
    }


}
