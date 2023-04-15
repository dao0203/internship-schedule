package com.schedule.eachinternshipschedule.util

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUtil {
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun getFirestoreInstance() = firestoreInstance
}