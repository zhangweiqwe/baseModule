package cn.wsgwz.basemodule.utilities.manager

import android.text.TextUtils
import cn.wsgwz.basemodule.BaseApplication
import cn.wsgwz.basemodule.data.User
import cn.wsgwz.basemodule.interfaces.listeners.OnUserStateChangeListener
import cn.wsgwz.basemodule.utilities.InjectorUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*

class UserManager private constructor() {


    //private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    private val userRepository = InjectorUtils.provideUserRepository(BaseApplication.getInstance())
    private val onUserStateChangeListeners = ArrayList<OnUserStateChangeListener>()

    private var currentUser: User? = null


    init {
        BaseApplication.getPreferences().getString("current_user_id", null).also {
            if (!TextUtils.isEmpty(it)) {
                runBlocking {
                    currentUser = userRepository.getUser(it)
                    Logger.t(TAG).v("userRepository.getUser(it)=$currentUser ${Thread.currentThread()}")
                }

            }
        }
    }

    fun getCurrentUser(): User? {
        return currentUser?.clone()
    }

    fun login(id: String, password: String? = null, token: String? = null) {
        login(id, null, password, token)
    }

    private fun login(id: String, name: String? = null, password: String? = null, token: String? = null) {
        currentUser = User(id, name, password, token).apply {
            runBlocking {
                //launch {
                    userRepository.insert(this@apply)
               // }
            }
            /*coroutineScope.launch {
                userRepository.insert(this@apply)
            }*/
            BaseApplication.getPreferences().edit().putString("current_user_id", id).apply()
        }

        onUserStateChangeListeners.forEach {
            it.onLoginSuccess()
        }


    }

    fun logout() {
        currentUser = null
        onUserStateChangeListeners.forEach {
            it.onLogoutSuccess()
        }
    }


    companion object {


        private const val TAG = "UserManager"
        // For Singleton instantiation
        @Volatile
        private var instance: UserManager? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: UserManager().also { instance = it }
            }
    }
}