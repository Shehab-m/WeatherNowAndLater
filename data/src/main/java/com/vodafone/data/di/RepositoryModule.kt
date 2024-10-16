package com.vodafone.data.di

import com.vodafone.core.repository.WeatherRepository
import com.vodafone.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

}