package com.example.azimnycschools.ui.screens.schools_list

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.ui.NYCSchoolsViewModel

@Composable
fun SchoolsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NYCSchoolsViewModel,
    onNavigateToDetails: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var listOfSchools: NYCSchools
    val dbSchools by viewModel.readSchoolsFromDB.observeAsState()
    val apiSchools by remember {viewModel.schoolsAPIData}

    if (dbSchools?.nycSchools == null) {
        viewModel.getSchoolsFromAPI()
        listOfSchools = apiSchools
    } else {
//        Log.i("data", "DB: ${dbUsers!!.usersModel.items[0]}")
        Log.i("data", "fetched data from DB")
        listOfSchools = dbSchools!!.nycSchools
    }

    Scaffold(
        topBar = {
            SearchBar(
                text = viewModel.searchTextState.value,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onSearchClicked = onSearchClicked
            )
        }
    ) { padding ->
        SchoolsList(listOfSchools, onNavigateToDetails)
    }
}

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = { onTextChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.White.copy(alpha = ContentAlpha.medium)
            ))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SchoolsList(
    nycSchools: NYCSchools,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(nycSchools) {school ->
            Card(
                onClick = { onNavigateToDetails(school.schoolName) },
                elevation = 10.dp,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = school.schoolName,
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun SearchBarPreview() {
//    GitHubUsersByAzimTheme { SearchBar(Modifier.padding(8.dp)) }
//}

//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
//@Composable
//fun UsersListPreview() {
//    GitHubUsersByAzimTheme {
//        UsersList(Users())
//    }
//}