package com.schedule.eachinternshipschedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.schedule.eachinternshipschedule.model.Schedule
import com.schedule.eachinternshipschedule.repository.FirestoreRepository
import kotlinx.coroutines.launch

class PostScheduleViewModel(private val firestoreRepository: FirestoreRepository) :ViewModel(){

    fun insertSchedule(schedule: Schedule){
        viewModelScope.launch {
            firestoreRepository.insertSchedule(schedule)
        }
    }
}

class PostScheduleModelFactory(private val repository: FirestoreRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostScheduleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
