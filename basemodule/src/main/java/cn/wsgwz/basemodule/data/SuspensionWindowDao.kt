package cn.wsgwz.basemodule.data

import androidx.room.*


@Dao
interface SuspensionWindowDao {
    @Query("SELECT * FROM suspension_window WHERE name=:name")
    fun getSuspensionWindow(name: String): SuspensionWindow?

    @Update
    fun update(suspensionWindow: SuspensionWindow)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun insert(suspensionWindow: SuspensionWindow)
}