package com.example.azimnycschools.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
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

    private val viewModel : NYCSchoolsViewModel by lazy {
        ViewModelProvider(this)[NYCSchoolsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsAzimTheme {
                Navigation(viewModel)
            }
        }
    }
}

@Composable
fun Navigation(viewModel: NYCSchoolsViewModel) {

    val navController = rememberNavController()

    val arguments = listOf(
        navArgument("schoolName") { type = NavType.StringType }
    )

    NavHost(navController = navController, startDestination = "schoolsList") {
        composable("schoolsList") {
            SchoolsListScreen(
                viewModel = viewModel,
                onNavigateToDetails = { schoolName ->
                    viewModel.getSchoolDetails(schoolName)
                    viewModel.getSchoolsSATsFromAPI()
                    navController.navigate("schoolDetails/$schoolName") },
                onSearchClicked = { schoolName ->
                    viewModel.getSchoolDetails(schoolName)
                    viewModel.getSchoolsSATsFromAPI()
                    navController.navigate("schoolDetails/$schoolName")}
            ) }
        composable("schoolDetails") {
            SchoolDetailsScreen(
                viewModel = viewModel,
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
                    viewModel = viewModel,
                    schoolName = schoolName,
                    onNavigateBack = {navController.navigate("schoolsList")}
                )
            }
        }
    }
}