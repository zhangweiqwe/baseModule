package cn.wsgwz.basemodule.widgets.suspension.bean

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface SuspensionWindowDao {
    @Query("SELECT * FROM suspension_window WHERE name=:name")
    fun getSuspensionWindow(name: String): SuspensionWindowBean?

    @Update
    fun update(suspensionWindow: SuspensionWindowBean)

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    fun insert(suspensionWindow: SuspensionWindowBean)
}