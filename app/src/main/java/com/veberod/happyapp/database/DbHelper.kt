package com.veberod.happyapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.joda.time.DateTime

class DbHelper(val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "my_database"
        const val DATABASE_VERSION = 1
    }

    // Create a SQLiteOpenHelper object to manage the database
    val dbHelper = DbHelper(context)

    // Get a writable instance of the database
    val db: SQLiteDatabase = dbHelper.writableDatabase
    //val timestamp = DateTime.now()


    override fun onCreate(db: SQLiteDatabase) {
        // Create the database tables
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)")
        db.execSQL("CREATE TABLE user_details (id INTEGER PRIMARY KEY, user_id INTEGER, gender TEXT, age INTEGER, FOREIGN KEY(user_id) REFERENCES users(id))")
        db.execSQL("CREATE TABLE mood_entries (id INTEGER PRIMARY KEY, user_id INTEGER, geolocation TEXT, mood_level INTEGER, timestamp DATETIME, comment TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades
        if (oldVersion < 2 && newVersion >= 2) {
            // Add a new column to the users table
            db.execSQL("ALTER TABLE users ADD COLUMN email TEXT")

            // Create a new table for storing user preferences
            db.execSQL("CREATE TABLE user_preferences (id INTEGER PRIMARY KEY, user_id INTEGER, preference_name TEXT, preference_value TEXT)")
        }
    }

    fun getUser(id: Int): User? {
        // Query the database for the user with the given ID
        val cursor = db.query("users", null, "id = ?", arrayOf(id.toString()), null, null, null)
        val user = if (cursor.moveToFirst()) {
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            val gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow("age"))
            User(userId, username, password, gender, age)
        } else {
            null
        }
        cursor.close()
        return user
    }

    fun insertUser(user: User) {
        // Insert the given user into the database
        val values = ContentValues()
        values.put("username", user.username)
        values.put("password", user.password)
        db.insert("users", null, values)
    }

    fun updateUser(user: User) {
        // Update the given user in the database
        val values = ContentValues()
        values.put("username", user.username)
        values.put("password", user.password)
        db.update("users", values, "id = ?", arrayOf(user.id.toString()))
    }

    fun deleteUser(id: Int) {
        // Delete the user with the given ID from the database
        db.delete("users", "id = ?", arrayOf(id.toString()))
    }

    fun getMoodEntries(userId: Int): List<MoodEntry> {
        // Query the database for the mood entries for the given user
        val cursor = db.query("mood_entries", null, "user_id = ?", arrayOf(userId.toString()), null, null, null)

        // Create a list to store the mood entries
        val moodEntries = mutableListOf<MoodEntry>()

        // Iterate over the results of the query and add each mood entry to the list
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"))
            val geolocation = cursor.getString(cursor.getColumnIndexOrThrow("geolocation"))
            val moodLevel = cursor.getInt(cursor.getColumnIndexOrThrow("mood_level"))
            val timestampString = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"))
            val timestamp = DateTime.parse(timestampString)
            val comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"))
            moodEntries.add(MoodEntry(id, userId, geolocation, moodLevel, timestamp, comment))
        }

        // Close the cursor and return the list of mood entries
        cursor.close()
        return moodEntries
    }
}