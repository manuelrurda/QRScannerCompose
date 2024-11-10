package com.manuelrurda.ejercicioopcionalcm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manuelrurda.ejercicioopcionalcm.screens.MainScreen
import com.manuelrurda.ejercicioopcionalcm.ui.theme.EjercicioOpcionalCMTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EjercicioOpcionalCMTheme {
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Main
                ){
                    composable<Main> {
                        MainScreen()
                    }
                }
            }
        }
    }
}

@Serializable
object Main