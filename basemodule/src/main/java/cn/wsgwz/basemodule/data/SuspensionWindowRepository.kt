package cn.wsgwz.basemodule.data

import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class SuspensionWindowRepository private constructor(
    private val suspensionWindowDao: SuspensionWindowDao
) {
    private val tag by lazy { SuspensionWindowRepository::class.java.simpleName }


    suspend fun getSuspensionWindow(name: String): SuspensionWindow?{
        return withContext(IO) {
            suspensionWindowDao.getSuspensionWindow(name)
        }
    }

    suspend fun update(suspensionWindow: SuspensionWindow){
        return withContext(IO) {
            /*Logger.t(tag).d("->"+Thread.currentThread())
            Thread.sleep(10000)
            Logger.t(tag).d("-->"+Thread.currentThread())*/
            suspensionWindowDao.update(suspensionWindow)
        }
    }

    suspend fun insert(suspensionWindow: SuspensionWindow){
        return withContext(IO) {
            suspensionWindowDao.insert(suspensionWindow)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: SuspensionWindowRepository? = null

        fun getInstance(suspensionWindowDao: SuspensionWindowDao) =
            instance ?: synchronized(this) {
                instance ?: SuspensionWindowRepository(suspensionWindowDao).also { instance = it }
            }
    }
}