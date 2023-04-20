package com.schedule.eachinternshipschedule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.schedule.eachinternshipschedule.data.repository.DefaultFirestoreRepository
import com.schedule.eachinternshipschedule.data.repository.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideFirestoreRepository(
        firestore: FirebaseFirestore
    ): FirestoreRepository{
        return DefaultFirestoreRepository(firestore)
    }
}