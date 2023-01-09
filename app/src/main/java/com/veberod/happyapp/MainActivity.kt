package com.veberod.happyapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.veberod.happyapp.database.domain.model.User
import com.veberod.happyapp.database.domain.repository.MoodRepository
import com.veberod.happyapp.feature_login.presentation.components.Login
import com.veberod.happyapp.feature_register.presentation.components.Register
import com.veberod.happyapp.feature_smilies.presentation.components.Smileys
import com.veberod.happyapp.feature_statistics.presentation.Statistics
import com.veberod.happyapp.feature_statistics.presentation.components.notifications.createNotification
import com.veberod.happyapp.map_feature.presentation.components.Map
import com.veberod.happyapp.screens.*
import com.veberod.happyapp.ui.theme.HappyAppTheme


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotification(this)
        setContent {
            HappyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(this)
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun App(context: Context) {
    HappyAppTheme {
        val navController = rememberNavController() //TODO Can it be something with this? the general navcontroller?
        val userState = remember { mutableStateOf(UserState())}
        Scaffold(
            content = { NavigationHost(navController, context, userState) },
            bottomBar = { BottomNavigationBar(navController, userState) }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun NavigationHost(
    navController: NavHostController,
    context: Context,
    userState: MutableState<UserState>
) {
    NavHost(navController = navController, startDestination = NavRoutes.Login.route) {
            composable(NavRoutes.Login.route) {
                Login(context, navController, userState)
            }
            composable(NavRoutes.Register.route) {
                Register(context, navController)
            }
            composable(NavRoutes.Smilies.route) {
                Smileys(moodRepository = MoodRepository(context), userState, context)
            }
            composable(NavRoutes.Map.route) {
                Map(/*moodRepository = MoodRepository(context)*/)
            }
            composable(NavRoutes.Statistics.route) {
                Statistics(moodRepository = MoodRepository(context), userState)
            }
            composable(NavRoutes.Settings.route) {
                Settings(context, navController ,userState)
            }
        }
    }
//}

@Composable
fun BottomNavigationBar(navController: NavHostController, userState: MutableState<UserState>) {
    BottomNavigation {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        if (userState.value.isLoggedIn) {
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
}


data class UserState(
    var isLoggedIn: Boolean = false,
    var user: User? = null,
    val isAdmin: Boolean = false
)

