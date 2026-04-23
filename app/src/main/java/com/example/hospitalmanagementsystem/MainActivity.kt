package com.example.hospitalmanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.example.hospitalmanagementsystem.Greeting
import com.example.hospitalmanagementsystem.navigation.AppNavHost



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Install the splash screen
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Optional: Keep splash on screen if you are checking auth status
        // splashScreen.setKeepOnScreenCondition { /* condition */ false }

        setContent {
            // Your AppNavHost goes here
            AppNavHost()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    // If HospitalManagementSystemTheme is missing, just call the composable directly
        Greeting("Android")
}

