package com.veberod.happyapp.feature_admin.domain.model

import androidx.room.*

@Entity(tableName = "mood",
    indices = [Index("user_id_in_mood")],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id_in_mood"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Mood(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mood_id") val moodId: Long,
    @ColumnInfo(name = "user_id_in_mood") val userId: Long,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "mood") val mood: String,
    @ColumnInfo(name = "comment") val comment: String
)