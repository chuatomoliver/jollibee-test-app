package com.certicode.jolibee_test_app.ui_screen

import com.certicode.jolibee_test_app.testdata.database.Quote


sealed class QuoteUiState {
    data class Success(val quotes: List<Quote>) : QuoteUiState()
    data object Loading : QuoteUiState()
    data class Error(val message: String) : QuoteUiState()
}
