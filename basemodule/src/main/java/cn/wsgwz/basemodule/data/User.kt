package cn.wsgwz.basemodule.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "users"
)
data class User(
        @PrimaryKey @ColumnInfo(name = "token") var token: String,
        @ColumnInfo(name = "phone") val phone: String? = null,
        @ColumnInfo(name = "password") var password: String? = null

) : Cloneable {


    public override fun clone(): User {
        return super.clone() as User
    }

    fun equals(user: User?): Boolean {
        return if (user == this) {
            true
        } else {
            user?.token.equals(this.token)
        }
    }
}