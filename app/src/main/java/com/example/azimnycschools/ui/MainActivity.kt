package com.example.azimnycschools.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.azimnycschools.ui.screens.school_details.SchoolDetailsScreen
import com.example.azimnycschools.ui.screens.schools_list.SchoolsListScreen
import com.example.azimnycschools.ui.theme.NYCSchoolsAzimTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsAzimTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {

    val navController = rememberNavController()

    val arguments = listOf(
        navArgument("schoolName") { type = NavType.StringType }
    )

    NavHost(navController = navController, startDestination = "schoolsList") {
        composable("schoolsList") {
            SchoolsListScreen(
                onNavigateToDetails = { schoolName ->
                    navController.navigate("schoolDetails/$schoolName") },
                onSearchClicked = { schoolName -> navController.navigate("schoolDetails/$schoolName")}
            ) }
        composable("schoolDetails") {
            SchoolDetailsScreen(
                onNavigateBack = {}
            ) }
        composable(
            route = "schoolDetails/{schoolName}",
            arguments = arguments
        ) { navBackStackEntry ->
            val schoolName =
                navBackStackEntry.arguments?.getString("schoolName")
            if (schoolName != null) {
                SchoolDetailsScreen(
                    schoolName = schoolName,
                    onNavigateBack = {navController.navigate("schoolsList")}
                )
            }
        }
    }
}