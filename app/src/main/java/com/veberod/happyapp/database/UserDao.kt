package com.veberod.happyapp.database

import androidx.room.*
import com.veberod.happyapp.feature_admin.domain.model.User
import com.veberod.happyapp.feature_admin.domain.model.UserWithMoods


@Dao
interface UserDao {
    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE user_id = :id")
    fun getById(id: Int): User

    @Query("SELECT * FROM user WHERE username = :username")
    fun getByUsername(username: String): User

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood WHERE user.user_id = :userId")
    fun getUserWithMoods(userId: Long): List<UserWithMoods>

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood")
    fun getUsersWithMoods(): List<UserWithMoods>
}
