package com.veberod.happyapp.database.domain.repository

import android.content.Context
import com.veberod.happyapp.database.DatabaseManager
import com.veberod.happyapp.database.domain.model.Mood

class MoodRepository(context: Context) {
    private val database = DatabaseManager.getInstance(context)
    private val moodDao = database.getMoodDao()

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