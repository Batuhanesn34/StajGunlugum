package com.example.stajgunlugum

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.stajgunlugum.navigation.AppNavigation
import com.example.stajgunlugum.userInterface.home.HomeScreen
import com.example.stajgunlugum.ui.theme.StajGunlugumTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            StajGunlugumTheme {
                AppNavigation()
            }
        }
    }
}