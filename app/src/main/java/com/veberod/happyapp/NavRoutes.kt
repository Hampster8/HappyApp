package com.veberod.happyapp

sealed class NavRoutes(val route: String) {
    object Register : NavRoutes("register")
    object Smilies : NavRoutes("smilies")
    object Map : NavRoutes("map")
    object Statistics : NavRoutes("statistics")
    object Settings : NavRoutes("settings")
    object Login : NavRoutes("login")
    object Admin : NavRoutes("admin")
}