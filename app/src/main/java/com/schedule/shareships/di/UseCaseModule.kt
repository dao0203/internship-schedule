package com.schedule.shareships.di

import com.schedule.shareships.data.repositories.ScheduleRepository
import com.schedule.shareships.usecases.PostScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun providePostScheduleUseCase(scheduleRepository: ScheduleRepository) =
        PostScheduleUseCase(scheduleRepository)
}
