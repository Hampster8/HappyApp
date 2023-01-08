package com.veberod.happyapp.database.domain.repository

import android.content.Context
import android.widget.Toast
import com.veberod.happyapp.database.DatabaseManager
import com.veberod.happyapp.database.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest


class UserRepository(context: Context, private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
    private val database = DatabaseManager.getInstance(context)
    private val userDao = database.getUserDao()


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

    suspend fun getByUsernameAndPassword(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            // Hash the password before querying the database
            val hashedPassword = hashPassword(password)
            // Query the database for a user with a matching username and password
            userDao.getByUsernameAndPassword(username, hashedPassword)
        }
    }

    suspend fun loadAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAll()
        }
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
        if (isUsernameTaken(user.username)) {
            showToast(context, "Username already taken")
            return false
        }
        if (isEmailTaken(user.email)) {
            showToast(context, "Email already taken")
            return false
        }
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

    suspend fun showToast(context: Context, message: String) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isEmailTaken(email: String): Boolean {
        return userDao.checkEmail(email) != null
    }

    private fun isUsernameTaken(username: String): Boolean {
        return userDao.checkUsername(username) != null
    }
}
