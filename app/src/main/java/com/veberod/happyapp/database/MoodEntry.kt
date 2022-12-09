package com.veberod.happyapp.database

import org.joda.time.DateTime

data class MoodEntry(
    val id: Int,
    val userId: Int,
    val geolocation: String,
    val moodLevel: Int,
    val timestamp: DateTime,
    val comment: String,
)