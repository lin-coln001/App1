package com.example.hospitalmanagementsystem.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hospitalmanagementsystem.ui.theme.screens.dashboard.DashboardScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.login.LoginScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.patient.AddPatientScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.register.RegisterScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_ADD_PATIENT
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 2. Register Screen
        composable(ROUTE_REGISTER) {
            RegisterScreen(navController)
        }

        // 3. Login Screen
        composable(ROUTE_LOGIN) {
            LoginScreen(navController)
        }

        // 4. Dashboard Screen (Now passing the navController correctly)
        composable(ROUTE_DASHBOARD) {
            DashboardScreen(navController)
        }
        composable(ROUTE_ADD_PATIENT){
            AddPatientScreen(navController)
        }
    }
}

