package com.veberod.happyapp.feature_admin.domain.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "user",
    indices = [Index("email", unique = true), Index("username", unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "f_name") val firstName: String,
    @ColumnInfo(name = "l_name") val lastName: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "gender") val gender: String, //Gender
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)

/*enum class Gender {
    MALE,
    FEMALE,
    NON_BINARY
}*/


