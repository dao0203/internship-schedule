package com.schedule.shareships.data.source

interface AccountService {
    fun getCurrentUser(): String?
    fun signIn(email: String, password: String)
    fun signUp(email: String, password: String)
    fun signOut()
    fun isSignedIn(): Boolean
}