package cn.wsgwz.basemodule.utilities.manager

import android.content.Intent
import android.text.TextUtils
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.BaseConst
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.utilities.InjectorUtils
import cn.wsgwz.basemodule.utilities.LLog
import cn.wsgwz.basemodule.utilities.retrofit.okHttp.interceptors.HeaderInterceptor
import kotlinx.coroutines.*

class UserManager private constructor() {

    enum class State {
        LOGIN_SUCCESS, LOGOUT_SUCCESS
    }

    //private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val userRepository = InjectorUtils.provideUserRepository(BaseApplication.getInstance())

    private var currentUser: User? = null


    init {
        BaseApplication.getPreferences().getString(BaseConst.PrefKey.CURRENT_USER_ID, null)?.also {
            runBlocking {
                currentUser = userRepository.getUser(it)
                LLog.d(TAG, "userRepository.getUser(it)=$currentUser ${Thread.currentThread()}")
            }

        }
    }


    companion object {


        private const val TAG = "UserManager"
        // For Singleton instantiation
        @Volatile
        private var instance: UserManager? = null

        @JvmStatic
        private fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: UserManager().also { instance = it }
                }

        @JvmStatic
        fun init() {
            getInstance()
        }


        @JvmStatic
        fun getCurrentUser(): User? {
            getInstance().currentUser?.also {
                synchronized(it) {
                    return it.clone()
                }
            }
            return null
        }

        @JvmStatic
        fun getCurrentUserToken(): String? {
            return getCurrentUser()?.token
        }

        @JvmStatic
        fun logout() {
            getInstance().currentUser = null
            BaseApplication.getPreferences().edit().putString(BaseConst.PrefKey.CURRENT_USER_ID, null).apply()
            LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(Intent(BaseConst.Action.USER_STATE_CHANGE).putExtra("state", State.LOGOUT_SUCCESS))
        }


        @JvmStatic
        fun login(token: String, phone: String? = null, password: String? = null) {
            if (getCurrentUser() == null) {
                getInstance().currentUser = User(token, phone, password).apply {
                    runBlocking {
                        getInstance().userRepository.insert(this@apply)
                    }
                }
                BaseApplication.getPreferences().edit().putString(BaseConst.PrefKey.CURRENT_USER_ID, getCurrentUser()?.token).apply()
                LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(Intent(BaseConst.Action.USER_STATE_CHANGE).putExtra("state", State.LOGIN_SUCCESS))
            }

        }
    }
}