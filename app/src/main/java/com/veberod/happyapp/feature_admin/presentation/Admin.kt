package com.veberod.happyapp.feature_admin.presentation

import android.content.Context
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.UserRepository
import kotlinx.coroutines.*

@Composable
fun Admin(userRepository: UserRepository, context: Context) {
    Column {
        val users = runBlocking {
            withContext(Dispatchers.IO) {
                userRepository.loadAllUsers()
            }
        }
        Text(modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Admin Board",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        UserList(users, userRepository)
    }


}

@Composable
fun UserList(users: List<User>, userRepository: UserRepository) {

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 50.dp)
    ) {
        users.forEach { user ->
            val firstNameState = mutableStateOf(user.firstName)
            var lastNameState = mutableStateOf(user.lastName)
            var emailState = mutableStateOf(user.email)
            var usernameState = mutableStateOf(user.username)
            var ageState = mutableStateOf(user.age.toString())
            var genderState = mutableStateOf(user.gender)
            var passwordState = mutableStateOf(user.password)
            var isAdminState = mutableStateOf(user.isAdmin)
            val isVisible = remember { mutableStateOf(true) }

            if (isVisible.value) {
                Card(modifier = Modifier.padding(8.dp), elevation = 8.dp) {
                    Column {
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = firstNameState.value,
                                onValueChange = { firstNameState.value = it },
                                label = { Text(text = "First Name") })
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = lastNameState.value,
                                onValueChange = { lastNameState.value = it },
                                label = { Text(text = "Last Name") })
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = usernameState.value,
                                onValueChange = { usernameState.value = it },
                                label = { Text(text = "Username") })
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = emailState.value,
                                onValueChange = { emailState.value = it },
                                label = { Text(text = "Email") })
                            Checkbox(
                                checked = isAdminState.value,
                                onCheckedChange = { isAdminState.value = it })
                        }
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth()
                        ) {
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = ageState.value,
                                onValueChange = { ageState.value = it },
                                label = { Text(text = "Age") })
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = genderState.value,
                                onValueChange = { genderState.value = it },
                                label = { Text(text = "Gender") })
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = "********",
                                onValueChange = { passwordState.value = it },
                                label = { Text(text = "Password") })

                        }
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.End)
                        ) {
                            Button(onClick = {
                                val scope = CoroutineScope(Dispatchers.Main)
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        val updatedUser = User(
                                            userId = user.userId,
                                            firstName = firstNameState.value,
                                            lastName = lastNameState.value,
                                            username = usernameState.value,
                                            email = emailState.value,
                                            isAdmin = isAdminState.value,
                                            age = ageState.value.toInt(),
                                            gender = genderState.value,
                                            password = userRepository.hashPassword(passwordState.value)
                                        )
                                        userRepository.update(updatedUser)
                                    }
                                }
                            }) {
                                Text(text = "Update")
                            }
                            Button(onClick = {
                                val scope = CoroutineScope(Dispatchers.Main)
                                scope.launch {
                                    isVisible.value = false
                                    fadeOut()
                                    withContext(Dispatchers.IO) {
                                        userRepository.delete(user)
                                    }
                                }
                            }) {
                                Text(text = "Delete")
                            }
                        }
                    }
                }
            }
        }

    }
}

