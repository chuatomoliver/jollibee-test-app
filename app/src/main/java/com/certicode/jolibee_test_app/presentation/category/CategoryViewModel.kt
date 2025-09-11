package com.certicode.jolibee_test_app.presentation.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.domain.categoryUseCase.CategoryUseCase
import com.certicode.jolibee_test_app.presentation.category.CategoryUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import com.certicode.jolibee_test_app.Result

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        // Start collecting categories from the use case as soon as the ViewModel is created.
        viewModelScope.launch {
            categoryUseCase.getAllCategories().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = if (result.data.isEmpty()) {
                            CategoryUiState.Empty
                        } else {
                            Success(result.data)
                        }
                    }
                    is Result.Error -> {
                        _uiState.value = Error(result.toString())
                    }
                    is Result.Loading -> {
                        _uiState.value = Loading
                    }
                    else -> {
                        // Handle other result types if necessary.
                    }
                }
            }
        }
    }

    /**
     * Adds a new category.
     * @param category The [CategoryModel] to be added.
     */
    fun addCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryUseCase.addCategory(category)
        }
    }

    /**
     * Deletes an existing category.
     * @param category The [CategoryModel] to be deleted.
     */
    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryUseCase.deleteCategory(category)
        }
    }

    /**
     * Updates an existing category.
     * @param category The [CategoryModel] to be updated.
     */
    fun updateCategory(category: CategoryModel) {
        viewModelScope.launch {
            categoryUseCase.updateCategory(category)
        }
    }
}
