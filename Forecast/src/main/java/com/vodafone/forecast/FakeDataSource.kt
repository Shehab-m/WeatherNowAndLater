package com.vodafone.forecast

import com.vodafone.core.domain.model.DayTimeWeather
import com.vodafone.core.domain.model.Main
import com.vodafone.core.domain.model.Weather
import com.vodafone.core.domain.model.Wind
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.random.Random

object FakeForecastDataSource {

    private fun generateSequentialDayTimeWeatherList(): List<DayTimeWeather> {
        val weatherData = mutableListOf<DayTimeWeather>()
        val random = Random.Default
        fun generateRandomTemp(): Double {
            val integerPart = random.nextInt(100, 290)
            val decimalPart = random.nextInt(0, 9)
            return "$integerPart.$decimalPart".toDouble()
        }
        for (day in 0 until 5) {
            val startOfDay = LocalDateTime.now().plusDays(day.toLong()).truncatedTo(ChronoUnit.DAYS)
            for (timeSlot in 0 until 10) {
                val dateTime = startOfDay.plusHours(timeSlot * 3L)
                val main = Main(
                    temp = generateRandomTemp(),
                    feelsLike = 290.0 + random.nextDouble(10.0),
                    tempMin = 290.0 + random.nextDouble(5.0),
                    tempMax = 290.0 + random.nextDouble(5.0),
                    pressure = 1000 + random.nextInt(50),
                    humidity = random.nextInt(30, 90),
                    grndLevel = random.nextInt(30, 90),
                    tempKf = random.nextDouble(30.0, 90.0),
                    seaLevel = random.nextInt(30, 90),
                )
                val weatherList = listOf(
                    Weather(
                        id = random.nextInt(800, 804),
                        main = listOf("Clouds", "Clear","Rain",).random(),
                        description = if (random.nextBoolean()) "few clouds" else "clear sky",
                        icon = if (random.nextBoolean()) "02n" else "01n"
                    )
                )
                val wind = Wind(
                    speed = "%.2f".format(random.nextDouble(0.0, 10.0)).toDouble(),
                    deg = random.nextInt(0, 360),
                )
                val visibility =
                    random.nextInt(5000, 10000)
                val dayTimeWeather = DayTimeWeather(
                    date = dateTime,
                    dateTxt = dateTime.toString(),
                    main = main,
                    visibility = visibility,
                    weather = weatherList,
                    wind = wind
                )

                weatherData.add(dayTimeWeather)
            }
        }

        return weatherData
    }

    fun getSortedForecast(): List<List<DayTimeWeather>> {
        val weatherData = generateSequentialDayTimeWeatherList()
        val now = LocalDateTime.now()

        return weatherData.groupBy { it.date.toLocalDate() }
            .map {
                it.value.sortedBy { dayWeather ->
                    Math.abs(dayWeather.date.until(now, ChronoUnit.MINUTES))
                }
            }.sortedBy { it.first().date.toLocalDate() }
    }


}