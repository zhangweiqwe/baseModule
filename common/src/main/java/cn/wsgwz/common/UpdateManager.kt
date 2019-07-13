package cn.wsgwz.common

import cn.wsgwz.basemodule.BaseConst

class UpdateManager private constructor() {
    private val okHttpClient = BaseConst.OK_HTTP_CLIENT.newBuilder().build()

    //fun checkUpdate()
    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: UpdateManager? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance
                            ?: UpdateManager().also { instance = it }
                }
    }
}