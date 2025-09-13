package com.certicode.jolibee_test_app.presentation.login

import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginResponse

sealed class LoginState {
    object Initial : LoginState()
    object Loading : LoginState()
    data class Success(val loginResponse: LoginResponse) : LoginState()
    data class Error(val message: String) : LoginState()
}