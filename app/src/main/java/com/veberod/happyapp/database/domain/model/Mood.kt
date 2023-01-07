package com.veberod.happyapp.database.domain.model

import androidx.room.*
import com.google.android.gms.maps.model.LatLng

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
    @ColumnInfo(name = "mood") val mood: Int,
    @ColumnInfo(name = "comment") val comment: String,
    @TypeConverters(LatLngConverter::class)
    @ColumnInfo(name = "geolocation") val geolocation: String
)

class LatLngConverter {
    @TypeConverter
    fun fromLatLng(latLng: LatLng): String {
        return "${latLng.latitude},${latLng.longitude}"
    }

    @TypeConverter
    fun toLatLng(value: String): LatLng {
        val parts = value.split(",")
        return LatLng(parts[0].toDouble(), parts[1].toDouble())
    }
}