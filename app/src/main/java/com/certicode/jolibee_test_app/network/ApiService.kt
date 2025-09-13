package com.certicode.jolibee_test_app.network

import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginRequest
import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    // Add other API endpoints here as needed
}