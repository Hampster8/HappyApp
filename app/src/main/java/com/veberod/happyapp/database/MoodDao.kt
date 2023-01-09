package com.veberod.happyapp.database

import androidx.room.*
import com.veberod.happyapp.database.domain.model.Mood
import com.veberod.happyapp.database.domain.model.UserWithMoods

@Dao
interface MoodDao {
    @Insert
    fun insert(mood: Mood)

    @Update
    fun update(mood: Mood)

    @Delete
    fun delete(mood: Mood)

    @Query("SELECT * FROM mood WHERE user_id_in_mood = :id")
    fun getMoodsById(id: Long): List<Mood>

    @Query("SELECT * FROM mood")
    fun getAllMoods(): List<Mood>

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password, isAdmin FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood WHERE user.user_id = :userId")
    fun getUserWithMoods(userId: Long): List<UserWithMoods>

    @Transaction
    @Query("SELECT user_id, f_name, l_name, username, gender, age, email, password, isAdmin FROM user LEFT JOIN mood ON user.user_id = mood.user_id_in_mood")
    fun getUsersWithMoods(): List<UserWithMoods>


}