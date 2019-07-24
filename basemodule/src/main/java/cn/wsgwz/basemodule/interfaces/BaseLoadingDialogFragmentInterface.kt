package cn.wsgwz.basemodule.interfaces

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cn.wsgwz.basemodule.widgets.dialog.LoadingDialogFragment

interface BaseLoadingDialogFragmentInterface {
    fun LoadingDialogFragment.showLoadingDialog(isCancellable: Boolean = false) {
        isCancelable = isCancellable
        when (this@BaseLoadingDialogFragmentInterface) {
            is AppCompatActivity -> {
                show(supportFragmentManager)
            }
            is Fragment -> {
                show(fragmentManager!!)
            }

            else->{
                throw Throwable("non support")
            }
        }
    }


    fun LoadingDialogFragment.dismissLoadingDialog() {
        dismiss()
    }
}