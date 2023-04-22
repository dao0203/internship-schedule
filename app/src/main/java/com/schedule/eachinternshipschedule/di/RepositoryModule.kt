package com.schedule.eachinternshipschedule.di

import com.schedule.eachinternshipschedule.data.repository.DefaultFirestoreRepository
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideFirestoreRepository(
        defaultFirestoreRepository: DefaultFirestoreRepository
    ): FirestoreRepository
}
