package com.veberod.happyapp.feature_admin.domain.repository

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.veberod.happyapp.database.HappyAppDB
import com.veberod.happyapp.database.UserDao
import com.veberod.happyapp.feature_admin.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest


class UserRepository(context: Context, private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
    private val database: HappyAppDB = Room.databaseBuilder(context, HappyAppDB::class.java, "happy_app_database")
        .build()
    private val userDao: UserDao = database.userDao()



    suspend fun insert(user: User, context: Context) {
        addUser(user, context)
    }

    suspend fun getById(id: Int): User {
        return userDao.getById(id)
    }

    suspend fun getByUsername(username: String): User {
        return userDao.getByUsername(username)
    }

    suspend fun update(user: User) {
        userDao.update(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray())
        return hash.toHexString()
    }

    private fun ByteArray.toHexString(): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(size * 2)
        for (i in indices) {
            val v = this[i].toInt() and 0xFF
            hexChars[i * 2] = hexArray[v ushr 4]
            hexChars[i * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    suspend fun addUser(user: User, context: Context): Boolean {
        return try {
            val hashedPassword = hashPassword(user.password)
            val userWithHashedPassword = user.copy(password = hashedPassword)
            userDao.insert(userWithHashedPassword)
            showToast(context, "User registered successfully")
            true
        } catch (e: Exception) {
            showToast(context, "Registration failed!")
            false
        }
    }

    private suspend fun showToast(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
