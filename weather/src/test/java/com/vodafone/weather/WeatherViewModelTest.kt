package com.vodafone.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vodafone.core.domain.usecase.GetCurrentWeatherUseCase
import com.vodafone.core.domain.usecase.GetLatestCityNameUseCase
import com.vodafone.core.domain.usecase.SaveCityNameUseCase
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase = mockk()
    private val getLatestCityNameUseCase: GetLatestCityNameUseCase = mockk()
    private val saveCityNameUseCase: SaveCityNameUseCase = mockk()

    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        weatherViewModel = WeatherViewModel(
            getCurrentWeatherUseCase,
            getLatestCityNameUseCase,
            saveCityNameUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onChangeSearchQuery should update searchQuery`() {
        val newQuery = "Updated City Name"

        weatherViewModel.onChangeSearchQuery(newQuery)

        assert(weatherViewModel.searchQuery == newQuery)
    }
}
