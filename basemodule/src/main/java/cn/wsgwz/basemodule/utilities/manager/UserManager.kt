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

    enum class UserState {
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


    fun getCurrentUser(): User? = currentUser ?: synchronized(this) {
            currentUser?.clone()
        }


    fun getCurrentUserToken(): String? {
        return getCurrentUser()?.token
    }


    fun logout() {
        currentUser = null
        BaseApplication.getPreferences().edit().putString(BaseConst.PrefKey.CURRENT_USER_ID, null).apply()
        LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(Intent(BaseConst.Action.USER_STATE_CHANGE).putExtra(USER_SATE_KEY, UserState.LOGOUT_SUCCESS))
    }


    fun login(token: String, phone: String? = null, password: String? = null) {
        currentUser = User(token, phone, password).apply {
            runBlocking {
                userRepository.insert(this@apply)
            }
        }
        BaseApplication.getPreferences().edit().putString(BaseConst.PrefKey.CURRENT_USER_ID, getCurrentUser()?.token).apply()
        LocalBroadcastManager.getInstance(BaseApplication.getInstance()).sendBroadcast(Intent(BaseConst.Action.USER_STATE_CHANGE).putExtra(USER_SATE_KEY, UserState.LOGIN_SUCCESS))
    }

    fun update(user: User) {
        currentUser = user.clone()
        runBlocking {
            userRepository.insert(user)
        }
    }


    companion object {

        private const val TAG = "UserManager"
        const val USER_SATE_KEY = "state"

        // For Singleton instantiation
        @Volatile
        private var instance: UserManager? = null

        @JvmStatic
        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: UserManager().also { instance = it }
                }


    }
}