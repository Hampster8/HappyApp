package com.veberod.happyapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.veberod.happyapp.feature_register.presentation.components.Register
import com.veberod.happyapp.map_feature.presentation.components.Map
import com.veberod.happyapp.screens.Settings
import com.veberod.happyapp.screens.Smilies
import com.veberod.happyapp.screens.Statistics
import com.veberod.happyapp.ui.theme.HappyAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    App(context = this)
                }
            }
        }
    }
}



@Composable
fun App(context: Context) {
    HappyAppTheme {
        val navController = rememberNavController()
        Scaffold(
            //topBar = { TopAppBar(title = { Text("HappyApp") }) },
            content = { NavigationHost(navController = navController, context) },
            bottomBar = { BottomNavigationBar(navController = navController) }
        )
    }
}

@Composable
fun NavigationHost(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = NavRoutes.Register.route) {
        composable(NavRoutes.Register.route) {
            Register(context)
        }
        composable(NavRoutes.Smilies.route) {
            Smilies()
        }
        composable(NavRoutes.Map.route) {
            Map()
        }
        composable(NavRoutes.Settings.route) {
            Settings()
        }
        composable(NavRoutes.Statistics.route) {
            Statistics()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}