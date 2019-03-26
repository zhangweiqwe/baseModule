package cn.wsgwz.basemodule.data

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users ORDER BY id")
    fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE id=:id")
    fun getUser(id: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)
}