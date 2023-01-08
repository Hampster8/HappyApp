package com.veberod.happyapp.database.domain.repository

import android.content.Context
import androidx.room.Room
import com.veberod.happyapp.database.HappyAppDB
import com.veberod.happyapp.database.MoodDao
import com.veberod.happyapp.database.domain.model.Mood

class MoodRepository(context: Context) {
    private val database: HappyAppDB = Room.databaseBuilder(context, HappyAppDB::class.java, "happy_app_database")
        /*.addMigrations(MIGRATION_2_3)*/
        .build()


    private val moodDao: MoodDao = database.moodDao()

    suspend fun getAllMoods(): List<Mood> {
        return moodDao.getAllMoods()
    }


    suspend fun insert(mood: Mood): Unit {
        moodDao.insert(mood)
    }

    suspend fun update(mood: Mood) {
        moodDao.update(mood)
    }

    suspend fun delete(mood: Mood) {
        moodDao.delete(mood)
    }
}