package cn.wsgwz.basemodule.data

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class UserRepository private constructor(
    private val userDao: UserDao
) {

    suspend fun getUsers(): List<User> {
       return withContext(IO) {
            userDao.getUsers()
        }
    }

    suspend fun getUser(id:String): User? {
        return withContext(IO) {
            userDao.getUser(id)
        }
    }

    suspend fun insert(user: User) {
        withContext(IO) {
            userDao.insert(user)
        }
    }

    suspend fun insertAll(users: List<User>) {
        withContext(IO) {
            userDao.insertAll(users = users)
        }
    }

    suspend fun delete(user: User) {
        withContext(IO) {
            userDao.delete(user)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(userDao: UserDao) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDao).also { instance = it }
            }
    }
}