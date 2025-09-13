package com.certicode.jolibee_test_app.presentation.login.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.data.jollibeedata.login.LoginResponse
import com.certicode.jolibee_test_app.data.jollibeedata.repository.AuthRepository
import com.certicode.jolibee_test_app.presentation.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel to handle login logic and UI state.
 */
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            Log.d("AuthViewModel", "Attempting login for user: $email")

            try {
                val response = repository.login(email, password)
                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = LoginState.Success(response.body()!!)
                    Log.d("AuthViewModel", "Login successful")
                } else {
                    val errorMessage = "Login Failed: ${response.message()}"
                    _loginState.value = LoginState.Error(errorMessage)
                    Log.e("AuthViewModel", errorMessage)
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unknown error occurred"
                _loginState.value = LoginState.Error("Login Error: $errorMessage")
                Log.e("AuthViewModel", "Login Error: $errorMessage", e)
            }
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Initial
    }
}
