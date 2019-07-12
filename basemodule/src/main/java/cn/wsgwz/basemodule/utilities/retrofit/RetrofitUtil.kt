package cn.wsgwz.basemodule.utilities.retrofit

import android.widget.Toast
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.R
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.manager.UserManager
import com.google.gson.JsonParseException
import java.io.IOException
import java.lang.NumberFormatException

object RetrofitUtil {
    private const val TAG = "RetrofitUtil"
    private fun getHint(throwable: Throwable): String {
        LLog.d(TAG,throwable::class.java.canonicalName)
        return when (throwable) {
            is IOException, is retrofit2.HttpException -> {
                BaseApplication.getInstance().getString(R.string.network_error_hint)
            }
            is JsonParseException, is NumberFormatException -> {
                BaseApplication.getInstance().getString(R.string.data_error_hint)
            }
            is IllegalArgumentException -> {
                BaseApplication.getInstance().getString(R.string.illegal_argument_hint)
            }
            else -> {
                BaseApplication.getInstance().getString(R.string.unknown_exception_hint)
            }
        }

    }


    private fun onObserverErrorToast(throwable: Throwable) {
        Toast.makeText(BaseApplication.getInstance(), getHint(throwable), Toast.LENGTH_SHORT).show()
    }

    private fun onServerErrorToast(serverException: ServerException) {
        Toast.makeText(BaseApplication.getInstance(), "${BaseApplication.getInstance().getString(R.string.operation_failed)}：${serverException.message}", Toast.LENGTH_SHORT).show()
    }


    fun checkLogin(code: Int) {
        if (code == Code.NO_LOGIN.code) {
            UserManager.logout()
            (BaseApplication.getInstance() as BaseApplication).login()
        }
    }


    fun onErrorToast(throwable: Throwable) {
        if (throwable is ServerException) {
            onServerErrorToast(throwable)
        } else {
            onObserverErrorToast(throwable)
        }
    }


    fun onSuccessToast(msg: String?) {
        msg?.also {
            if (it.isNotEmpty()) {
                Toast.makeText(BaseApplication.getInstance(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}