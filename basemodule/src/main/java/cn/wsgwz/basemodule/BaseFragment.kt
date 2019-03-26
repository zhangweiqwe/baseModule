package cn.wsgwz.basemodule

import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : Fragment(), BaseWindowInterface {

    override fun toast(charSequence: CharSequence?) {
        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        toast(context?.getString(resId))
    }
}