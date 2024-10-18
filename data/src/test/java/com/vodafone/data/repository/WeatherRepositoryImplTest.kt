package com.vodafone.data.repository

import com.vodafone.data.local.DataStoreManager
import com.vodafone.data.remote.WeatherApiService
import com.vodafone.data.remote.model.CityWeatherDto
import com.vodafone.data.remote.model.WeatherForecastDto
import com.vodafone.data.repository.mapper.toEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
@Config(sdk = [28])
class WeatherRepositoryImplTest {

    @Mock
    private lateinit var weatherApiService: WeatherApiService

    @Mock
    private lateinit var dataStoreManager: DataStoreManager

    private lateinit var weatherRepository: WeatherRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherRepository = WeatherRepositoryImpl(weatherApiService, dataStoreManager)
    }

    @Test
    fun `getCurrentWeather returns successful response`() = runTest {
        val cityName = "London"
        val cityWeatherDto =
            CityWeatherDto("base", 200, null, 123, 1, null, "London", null, null, null, null, null)
        val cityWeather = cityWeatherDto.toEntity()

        `when`(weatherApiService.getCurrentWeather(cityName)).thenReturn(
            Response.success(
                cityWeatherDto
            )
        )

        val result = weatherRepository.getCurrentWeather(cityName)
        assertEquals(cityWeather, result)
    }

    @Test
    fun `getCurrentWeather throws exception on error response`() = runTest {
        val cityName = ""
        val errorMessage = "Response.error()"
        val errorResponse = Response.error<CityWeatherDto>(404, mock())

        `when`(weatherApiService.getCurrentWeather(cityName)).thenReturn(errorResponse)

        val exception = assertFailsWith<Exception> {
            weatherRepository.getCurrentWeather(cityName)
        }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getWeatherForecast returns successful response`() = runTest {
        val cityName = "London"
        val weatherForecastDto = WeatherForecastDto(null, null, null, null, null)
        val weatherForecast = weatherForecastDto.toEntity()

        `when`(weatherApiService.getWeatherForecast(cityName)).thenReturn(
            Response.success(
                weatherForecastDto
            )
        )

        val result = weatherRepository.getWeatherForecast(cityName)
        assertEquals(weatherForecast, result)
    }

    @Test
    fun `getWeatherForecast throws exception on error response`() = runTest {
        val cityName = ""
        val errorMessage = "Response.error()"
        val errorResponse = Response.error<WeatherForecastDto>(404, mock())

        `when`(weatherApiService.getWeatherForecast(cityName)).thenReturn(errorResponse)

        val exception = assertFailsWith<Exception> {
            weatherRepository.getWeatherForecast(cityName)
        }
        assertEquals(errorMessage, exception.message)
    }

    @Test
    fun `getCityName returns correct city name`() = runTest {
        val cityName = "London"

        `when`(dataStoreManager.getCityName()).thenReturn(cityName)

        val result = weatherRepository.getCityName()
        assertEquals(cityName, result)
    }

    @Test
    fun `getCityName returns null`() = runTest {
        val cityName = null

        `when`(dataStoreManager.getCityName()).thenReturn(cityName)

        val result = weatherRepository.getCityName()
        assertEquals(cityName, result)
    }

    @Test
    fun `saveCityName stores city name correctly`() = runTest {
        val cityName = "Paris"

        weatherRepository.saveCityName(cityName)

        verify(dataStoreManager, times(1)).saveCityName(cityName)
    }
}
