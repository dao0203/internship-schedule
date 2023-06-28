package com.schedule.shareships.data.sources

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    firebaseAuth: FirebaseAuth
) : AccountService {
    override fun getCurrentUser(): String? {
        TODO("Not yet implemented")
    }

    override fun signIn(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun isSignedIn(): Boolean {
        TODO("Not yet implemented")
    }
}