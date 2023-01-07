package com.veberod.happyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.veberod.happyapp.database.domain.model.LatLngConverter
import com.veberod.happyapp.database.domain.model.Mood
import com.veberod.happyapp.database.domain.model.User

@Database(entities = [User::class, Mood::class], version = 3/*, exportSchema = false*/)
@TypeConverters(LatLngConverter::class)
abstract class HappyAppDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun moodDao(): MoodDao
}


