package com.example.azimnycschools.ui.screens.school_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.azimnycschools.models.sats.Sats
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.models.schools.NYCSchoolsItemModel
import com.example.azimnycschools.ui.NYCSchoolsViewModel

@Composable
fun SchoolDetailsScreen(
    schoolName: String = "",
    viewModel: NYCSchoolsViewModel,
    onNavigateBack: () -> Unit
) {
    val schoolDetails by remember {viewModel.schoolDetailsApiData}
    val schoolSATDetails by remember {viewModel.schoolSATsApiData}

    viewModel.getSchoolDetails(schoolName)

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.primary
            ) {
                Row {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            }
        }
    ) { padding ->
        Column {
            SchoolDetails(schoolSATDetails, schoolDetails)
        }
    }


}

@Composable
fun SchoolDetails(sats: Sats, nycSchool: NYCSchoolsItemModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1)
    ) {
        item { Text(text = "Name: ${nycSchool.schoolName}") }
        item { Text(text = "Email: ${nycSchool.schoolEmail}") }
        item { Text(text = "Location: ${nycSchool.location}") }
        item { Text(text = "Campus: ${nycSchool.campusName}") }
        item { Text(text = "Website: ${nycSchool.website}") }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun FollowersSection(
//    followers: Followers,
//    onNavigateToFollower: (String) -> Unit,
//) {
//    LazyRow {
//        items(followers) { follower ->
//            Surface(
//                modifier = Modifier.fillMaxWidth(),
//                onClick = { onNavigateToFollower(follower.login) }
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(follower.avatarUrl)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .clip(CircleShape)
//                            .size(128.dp)
//                    )
//                    Text(
//                        text = follower.login,
//                        style = MaterialTheme.typography.h5,
//                    )
//                }
//            }
//        }
//    }
//}