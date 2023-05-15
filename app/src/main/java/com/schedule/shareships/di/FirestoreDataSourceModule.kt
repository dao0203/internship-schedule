package com.schedule.shareships.di

import com.schedule.shareships.data.source.FirestoreDataSource
import com.schedule.shareships.data.source.FirestoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirestoreDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindFirestoreDataSource(
        firestoreDataSourceImpl: FirestoreDataSourceImpl
    ): FirestoreDataSource

}