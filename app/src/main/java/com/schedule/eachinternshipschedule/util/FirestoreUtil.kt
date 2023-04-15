package com.schedule.eachinternshipschedule.util

import com.google.firebase.firestore.FirebaseFirestore

object FirestoreInstanceUtil {
    private val firestoreInstance: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun get() = firestoreInstance
}