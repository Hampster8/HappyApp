package com.veberod.happyapp.feature_admin.presentation

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun Admin(userRepository: UserRepository, context: Context) {
    val scope = CoroutineScope(Dispatchers.Main)

    Text(text = "Welcome to the Admin Panel")
    Column() {
        val users = runBlocking {
            withContext(Dispatchers.IO) {
                userRepository.loadAllUsers()
            }
        }
        UserList(users)
    }


}

@Composable
fun UserList(users: List<User>) {
    Column {
        users.forEach { user ->
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Name: ${user.firstName} ${user.lastName}")
                Text("Username: ${user.username}")
                Text("Email: ${user.email}")
                Text("Admin: ${user.isAdmin}")
            }

        }
    }
}
