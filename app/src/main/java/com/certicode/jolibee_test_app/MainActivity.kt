package com.certicode.jolibee_test_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.certicode.jolibee_test_app.screens.LoginScreen
import com.certicode.jolibee_test_app.ui.theme.QuoteAppTheme
import com.certicode.jolibee_test_app.ui_screen.QuoteScreen
import dagger.hilt.android.AndroidEntryPoint


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            JollibeetestappTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MainApp() // Call the main composable that contains the NavHost
//                }
//            }
//        }
//    }
//}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuoteAppTheme {
                MainApp()
            }
        }
    }
}