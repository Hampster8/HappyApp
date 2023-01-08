package com.veberod.happyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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


class DatabaseManager private constructor(context: Context) {

    private val database: HappyAppDB = Room.databaseBuilder(context, HappyAppDB::class.java, "happy_app_database")
        .build()

    fun getUserDao(): UserDao = database.userDao()
    fun getMoodDao(): MoodDao = database.moodDao()

    companion object {

        private var instance: DatabaseManager? = null

        @Synchronized
        fun getInstance(context: Context): DatabaseManager {
            if (instance == null) {
                instance = DatabaseManager(context.applicationContext)
            }
            return instance!!
        }
    }
}