package cn.wsgwz.basemodule.widgets.suspension.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(
    tableName = "suspension_window"
)
data class SuspensionWindowBean(
        @PrimaryKey @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "x") var x: Int,
        @ColumnInfo(name = "y") var y: Int

)