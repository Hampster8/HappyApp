package com.veberod.happyapp.database.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithMoods(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "user_id_in_mood"
    )
    val moods: List<Mood>
)

