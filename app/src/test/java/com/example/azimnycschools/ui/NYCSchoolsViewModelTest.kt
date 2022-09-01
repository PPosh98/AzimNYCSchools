package com.example.azimnycschools.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.azimnycschools.models.schools.NYCSchools
import com.example.azimnycschools.models.schools.NYCSchoolsItemModel
import com.example.azimnycschools.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NYCSchoolsViewModelTest {

    val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository
    private lateinit var viewModel: NYCSchoolsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = NYCSchoolsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSchoolsFromAPI_returnsSuccess() = runBlocking {
        val expectedResult = Response.success(NYCSchools())

        whenever(repository.getSchoolsFromAPI()).thenReturn(expectedResult)

        viewModel.getSchoolsFromAPI()

        val result = repository.getSchoolsFromAPI()

        assertEquals(expectedResult, result)
    }

//    @Test
//    fun getDefaultUsersFromAPI_returnsError() = runBlocking {
//        whenever(repository.getDefaultUsersFromAPI()).thenReturn(Response.error(404, EMPTY_RESPONSE))
//
//        viewModel.getDefaultUsers()
//
////        assertEquals(Response.error(404, EMPTY_RESPONSE), repository.getDefaultUsersFromAPI())
//    }

    @Test
    fun getSchoolsFromAPI_returnsData() = runBlocking {
        val expectedResult = Response.success(getSchools())

        whenever(repository.getSchoolsFromAPI()).thenReturn(expectedResult)

        viewModel.getSchoolsFromAPI()

        assertEquals(getSchools(), viewModel.schoolsAPIData)
    }

    private fun getSchools(): NYCSchools {
        val nycSchoolsItemModel = NYCSchoolsItemModel(
            schoolEmail = "School of Animals",
            city = "Animal City",
            location = "64 Zoo Lane",
            campusName = "Zoo",
            website = "www.schoolofanimals.com"
        )
        val schools = NYCSchools()
        schools.add(nycSchoolsItemModel)
        schools.add(nycSchoolsItemModel)
        return schools
    }
}