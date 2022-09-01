package com.example.azimnycschools.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.azimnycschools.repository.Repository
import com.example.azimnycschools.roomdb.NYCSchoolsEntity
import com.example.azimnycschools.models.sats.Sats
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.models.schools.NYCSchoolsItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NYCSchoolsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    private val _schoolsAPIData: MutableState<NYCSchools> = mutableStateOf(NYCSchools())
    val schoolsAPIData: State<NYCSchools> get() = _schoolsAPIData

    private val _schoolDetailsApiData: MutableState<NYCSchoolsItemModel> = mutableStateOf(NYCSchoolsItemModel())
    val schoolDetailsApiData: State<NYCSchoolsItemModel> get() = _schoolDetailsApiData

    private val _schoolSATsApiData: MutableState<Sats> = mutableStateOf(Sats())
    val schoolSATsApiData: State<Sats> get() = _schoolSATsApiData

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

//    lateinit var schoolsData: NYCSchools
//
//    fun saveSchools(schools: NYCSchools) {
//        if (::schoolsData.isInitialized) {
//            schoolsData = schools
//        }
//    }

    val readSchoolsFromDB: LiveData<NYCSchoolsEntity> = repository.getSchoolsFromDB().asLiveData()

    fun getSchoolsFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSchoolsFromAPI()
            Log.i("data", "data fetched from API!")
            if(response.isSuccessful) {
                response.body()?.let {
                    _schoolsAPIData.value = it
//                    Log.i("data", "API: ${it.items[0]}")
                    addSchoolsToDB(it)
                }
            }
        }
    }

    fun getSchoolDetails(schoolName: String) {
        var data = _schoolsAPIData.value.find { it.schoolName == schoolName }
        Log.i("data", "API: ${_schoolsAPIData.value}")
        Log.i("data", "find API: ${data.toString()}")
    }

    private fun addSchoolsToDB(nycSchools: NYCSchools) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addSchoolsToDB(NYCSchoolsEntity(nycSchools))
        }
    }

}