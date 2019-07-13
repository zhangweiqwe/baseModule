package cn.wsgwz.basemodule.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY phone")
    fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE token=:token")
    fun getUser(token: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

}