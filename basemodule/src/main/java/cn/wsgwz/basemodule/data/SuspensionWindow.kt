package cn.wsgwz.basemodule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "suspension_window"
)
data class SuspensionWindow(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "x") var x: Int,
    @ColumnInfo(name = "y") var y: Int

)