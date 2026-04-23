package com.example.hospitalmanagementsystem.navigation

import android.R.attr.type
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hospitalmanagementsystem.ui.theme.screens.dashboard.DashboardScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.login.LoginScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.patient.AddPatientScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.patient.PatientListScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.patient.UpdatePatientScreen
import com.example.hospitalmanagementsystem.ui.theme.screens.register.RegisterScreen


@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "register"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("register"){RegisterScreen(navController)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController)
        }
        composable("add"){
            AddPatientScreen(navController)
        }
        composable("patientlist"){
            PatientListScreen(navController)
        }
        composable("updatepatient/{patientId}",
            arguments = listOf(
                navArgument("patientId")
                {type = NavType.StringType})){
                    backstackEntry ->
            val patientId = backstackEntry.arguments?.getString("patientId")!!
            UpdatePatientScreen(navController,patientId)

        }

}}

