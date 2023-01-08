package com.veberod.happyapp.feature_admin.presentation

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*@Composable
fun UserCrudBoard(userRepository: UserRepository, context: Context) {
    val selectedUser = remember { mutableStateOf<User?>(null) }
    val scope = CoroutineScope(Dispatchers.Default)
    val userList = remember { mutableStateOf<List<User>>(emptyList()) }

    // Load the list of users from the userRepository
    scope.launch {
        val users = userRepository.loadAllUsers()
        userList.value = users
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(userList.value) { user ->
            UserCard(
                user = user,
                onEdit = { selectedUser.value = user },
                onDelete = {
                    scope.launch { userRepository.delete(user) }
                }
            )
        }
    }

    if (selectedUser.value != null) {
        UserForm(
            user = selectedUser.value!!,
            onSave = {
                scope.launch { userRepository.update(selectedUser.value!!) }
            },
            onDelete = {
                scope.launch { userRepository.delete(selectedUser.value!!) }
            },
            onSubmit = {
                scope.launch { userRepository.insert(selectedUser.value!!, context) }
            },
            onClose = { selectedUser.value = null }
        )
    }
}

@Composable
fun UserCard(user: User, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.clickable(onClick = onEdit)) {
        Column {
            Text(text = "${user.firstName} ${user.lastName}")
            Text(text = user.email)
            Text(text = user.username)
            Text(text = user.gender)
            Text(text = user.age.toString())
        }
    }
    Button(onClick = onDelete) {
        Text("Delete")
    }
}

@Composable
fun UserForm(
    user: User,
    onSubmit: (User) -> Unit,
    onDelete: () -> Unit
) {
    val firstName = remember { mutableStateOf(user.firstName) }
    val lastName = remember { mutableStateOf(user.lastName) }
    val username = remember { mutableStateOf(user.username) }
    val gender = remember { mutableStateOf(user.gender) }
    val age = remember { mutableStateOf(user.age) }
    val email = remember { mutableStateOf(user.email) }
    val password = remember { mutableStateOf(user.password) }
    val isAdmin = remember { mutableStateOf(user.isAdmin) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text("First Name") }
        )
        TextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text("Last Name") }
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") }
        )
        TextField(
            value = gender.value,
            onValueChange = { gender.value = it },
            label = { Text("Gender") }
        )
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") }
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") }
        )
        Checkbox(
            checked = isAdmin.value,
            onCheckedChange = { isAdmin.value = it }
        )

        Text("Is Admin")

        Button(
            onClick = {
                onSubmit(
                    User(
                        user.userId,
                        firstName.value,
                        lastName.value,
                        username.value,
                        gender.value,
                        age.value,
                        email.value,
                        password.value,
                        isAdmin.value
                    )
                )
            }
        ) {
            Text("Submit")
        }
        Button(onClick = onDelete) {
            Text("Delete")
        }
    }
}*/



