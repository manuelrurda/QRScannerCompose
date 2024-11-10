package com.manuelrurda.ejercicioopcionalcm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
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