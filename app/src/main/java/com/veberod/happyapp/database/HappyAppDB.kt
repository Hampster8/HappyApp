package com.veberod.happyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.veberod.happyapp.feature_admin.domain.model.Mood
import com.veberod.happyapp.feature_admin.domain.model.User

@Database(entities = [User::class, Mood::class], version = 1)
abstract class HappyAppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moodDao(): MoodDao
}

