package cn.wsgwz.basemodule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class User(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "token") var token: String? = null

) : Cloneable {

    public override fun clone(): User {
        return super.clone() as User
    }

    fun equals(user: User?): Boolean {
        return if (user == this) {
            true
        } else {
            user?.id.equals(this.id)
        }
    }
}