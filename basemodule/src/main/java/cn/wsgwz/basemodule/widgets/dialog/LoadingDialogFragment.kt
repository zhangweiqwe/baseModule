package cn.wsgwz.basemodule.widgets.dialog

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.utilities.LLog
import kotlinx.android.synthetic.main.fragment_dialog_loading.*
import java.lang.Exception


class LoadingDialogFragment : DialogFragment() {
    companion object {
        private const val TAG = "LoadingDialogFragment"
    }


    init {
        LLog.d(TAG,"init")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.LoadingDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_loading, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parent_cl.postDelayed({
            if (isAdded || isVisible) {
                parent_cl.visibility = View.VISIBLE
            }
        }, 800)
    }

    fun show(manager: FragmentManager) {
        //Logger.d( "$isAdded $isVisible $isRemoving $isHidden ${manager.findFragmentByTag(TAG)}")

        if (isAdded || isVisible) {

        } else {
            show(manager, TAG)
            manager.executePendingTransactions()
        }
    }

    override fun show(manager: FragmentManager, tag: String?) {
        //super.show(manager, tag)

        try {
            super.show(manager, tag)
            //} catch (ignore: IllegalStateException) {
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}