package com.veberod.happyapp.database

import androidx.room.*
import com.veberod.happyapp.feature_admin.domain.model.Mood
import com.veberod.happyapp.feature_admin.domain.model.UserWithMoods

@Dao
interface MoodDao {
    @Insert
    fun insert(mood: Mood)

    @Update
    fun update(mood: Mood)

    @Delete
    fun delete(mood: Mood)

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood WHERE user.user_id = :userId")
    fun getUserWithMoods(userId: Long): List<UserWithMoods>

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood")
    fun getUsersWithMoods(): List<UserWithMoods>


}