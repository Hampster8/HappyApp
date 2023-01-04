package com.veberod.happyapp.feature_admin.domain.repository

import android.content.Context
import androidx.room.Room
import com.veberod.happyapp.database.HappyAppDB
import com.veberod.happyapp.database.MoodDao
import com.veberod.happyapp.feature_admin.domain.model.Mood

class MoodRepository(context: Context) {
    private val database: HappyAppDB = Room.databaseBuilder(context, HappyAppDB::class.java, "happy_app_database")
        .build()

    private val moodDao: MoodDao = database.moodDao()




    suspend fun insert(mood: Mood) {
        moodDao.insert(mood)
    }

/*    suspend fun getByUserId(userId: Int): List<Mood> {
        return moodDao.getByUserId(userId)
    }*/

    suspend fun update(mood: Mood) {
        moodDao.update(mood)
    }

    suspend fun delete(mood: Mood) {
        moodDao.delete(mood)
    }
}