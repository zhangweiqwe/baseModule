package cn.wsgwz.basemodule

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.interfaces.BaseWindowInterface
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

open class BaseFragment : Fragment(), BaseWindowInterface, HasSupportFragmentInjector {

    @Inject
    internal lateinit var androidInjector: DispatchingAndroidInjector<Fragment>


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun toast(charSequence: CharSequence?) {
        Toast.makeText(context, charSequence, Toast.LENGTH_SHORT).show()
    }

    override fun toast(resId: Int) {
        toast(context?.getString(resId))
    }

    override fun supportFragmentInjector() = androidInjector
}