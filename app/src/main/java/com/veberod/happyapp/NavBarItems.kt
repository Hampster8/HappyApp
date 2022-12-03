package com.veberod.happyapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Register",
            image = Icons.Filled.Home,
            route = "register"
        ),
        BarItem(
            title = "Smilies",
            image = Icons.Filled.Face,
            route = "smilies"
        ),
        BarItem(
            title = "Map",
            image = Icons.Filled.Favorite,
            route = "map"
        ),
        BarItem(
            title = "Statistics",
            image = Icons.Filled.Favorite,
            route = "statistics"
        ),
        BarItem(
            title = "Settings",
            image = Icons.Filled.Favorite,
            route = "settings"
        )

    )
}