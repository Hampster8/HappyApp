package com.veberod.happyapp.database

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val gender: String,
    val age: Int,
    val isAdmin: Boolean
)