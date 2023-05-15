package com.schedule.shareships.di

import com.schedule.shareships.data.repository.DataSourceRepositoryImpl
import com.schedule.shareships.data.repository.DataSourceRepository
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
        defaultFirestoreRepository: DataSourceRepositoryImpl
    ): DataSourceRepository
}
