package com.certicode.jolibee_test_app.data.jollibeedata.repository


import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginRequest
import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginResponse
import com.certicode.jolibee_test_app.network.ApiService
import retrofit2.Response

/**
 * Repository module for handling data operations.
 * It abstracts the data sources (like network calls) from the rest of the app.
 */
class AuthRepository(private val apiService: ApiService) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        return apiService.login(email, password)
    }
}
