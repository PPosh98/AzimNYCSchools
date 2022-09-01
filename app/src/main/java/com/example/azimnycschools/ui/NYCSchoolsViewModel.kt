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
import com.example.azimnycschools.models.sats.SatsItemModel
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

    private val _schoolsSATsAPIData: MutableState<Sats> = mutableStateOf(Sats())
    val schoolsSATsAPIData: State<Sats> get() = _schoolsSATsAPIData

    private val _schoolSATsAPIData: MutableState<SatsItemModel> = mutableStateOf(SatsItemModel())
    val schoolSATsAPIData: State<SatsItemModel> get() = _schoolSATsAPIData

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

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

    fun getSchoolsSATsFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getSchoolsSATsFromAPI()
            Log.i("data", "data fetched from API!")
            if(response.isSuccessful) {
                response.body()?.let {
                    _schoolsSATsAPIData.value = it
                }
            }
        }
    }

    fun getSchoolDetails(schoolName: String) {
        val schoolsData = _schoolsAPIData.value.find { it.schoolName == schoolName }
        val schoolsSATsData = _schoolsSATsAPIData.value.find { it.schoolName == schoolName.uppercase() }
        schoolsData?.let {_schoolDetailsApiData.value = it}
        schoolsSATsData?.let {_schoolSATsAPIData.value = it}
        Log.i("data", "API: ${_schoolsSATsAPIData.value}")
        Log.i("data", "find API: ${schoolsSATsData.toString()}")
    }

    private fun addSchoolsToDB(nycSchools: NYCSchools) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.addSchoolsToDB(NYCSchoolsEntity(nycSchools))
        }
    }

}