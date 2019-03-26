package cn.wsgwz.basemodule.utilities

import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.R
import java.io.IOException

object RetrofitUtil {

    fun getHint(throwable: Throwable): String {
        return BaseApplication.getInstance().getString(
            when (throwable) {
                is IOException -> {
                    R.string.network_error_hint
                }
                else -> {
                    R.string.data_error_hint
                }
            }
        )
    }
}