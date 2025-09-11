package com.certicode.jolibee_test_app.presentation.category

import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel

sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val categories: List<CategoryModel>) : CategoryUiState()
    object Empty : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}