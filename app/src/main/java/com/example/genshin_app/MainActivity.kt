package com.example.genshin_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.genshin_app.ui.navigation.NavGraph
import com.example.genshin_app.ui.theme.genshin_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as GenshinApplication

        setContent {
            genshin_appTheme {
                NavGraph(
                    navController = rememberNavController(),
                    container = app.container,
                )
            }
        }
    }
}