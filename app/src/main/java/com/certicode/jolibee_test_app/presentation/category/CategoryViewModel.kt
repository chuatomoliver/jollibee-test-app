package com.certicode.jolibee_test_app.data.jollibeedata.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.domain.categoryUseCase.CategoryUseCase
import com.certicode.jolibee_test_app.presentation.category.CategoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    // A private mutable state flow to hold the list of categories and their UI state.
    private val _categoryState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    // A public immutable state flow for the UI to observe.
    val categoryState: StateFlow<CategoryUiState> = _categoryState.asStateFlow()

    init {
        // Collect all categories from the use case when the ViewModel is initialized.
        getAllCategory()
    }


    fun getAllCategory() {
        viewModelScope.launch {
            categoryUseCase.getAllCategories().collectLatest { result ->
                _categoryState.value = when (result) {
                    is Result.Loading -> CategoryUiState.Loading
                    is Result.Success -> {
                        if (result.data.isEmpty()) {
                            CategoryUiState.Empty
                        } else {
                            CategoryUiState.Success(result.data)
                        }
                    }

                    is Result.Error -> CategoryUiState.Error("Failed to load categories.")
                }
            }
        }
    }

    /**
     * Resets the categories state to the initial loading state.
     * This is useful for preventing transient states from persisting on screen.
     */
    fun resetCategoryState() {
        _categoryState.value = CategoryUiState.Loading
    }

    /**
     * Adds a new category to the database.
     * @param category The [CategoryModel] to be added.
     */
    fun addCategory(category: CategoryModel) {
        viewModelScope.launch {
            val result = categoryUseCase.addCategory(category)
            _categoryState.value = when (result) {
                is Result.Success -> CategoryUiState.CategoryAdded
                is Result.Error -> CategoryUiState.Error("Failed to add category.")
                is Result.Loading -> CategoryUiState.Loading
            }
        }
    }

    /**
     * Deletes a category from the database.
     * @param category The [CategoryModel] to be deleted.
     */
    fun deleteCategory(category: CategoryModel) {
        viewModelScope.launch {
            val result = categoryUseCase.deleteCategory(category)
            _categoryState.value = when (result) {
                is Result.Success -> CategoryUiState.CategoryDeleted
                is Result.Error -> CategoryUiState.Error("Failed to delete category.")
                is Result.Loading -> CategoryUiState.Loading
            }
        }
    }

    /**
     * Updates an existing category in the database.
     * @param category The [CategoryModel] to be updated.
     */
    fun updateCategory(category: CategoryModel) {
        viewModelScope.launch {
            val result = categoryUseCase.updateCategory(category)
            _categoryState.value = when (result) {
                is Result.Success -> CategoryUiState.CategoryUpdated
                is Result.Error -> CategoryUiState.Error("Failed to update category.")
                is Result.Loading -> CategoryUiState.Loading
            }
        }
    }

    /**
     * Retrieves a single category by its ID and updates the state.
     * This is useful for screens that need to display a single category for editing.
     * @param id The ID of the category to retrieve.
     */
    fun getCategoryById(id: Long) {
        viewModelScope.launch {
            // First, set the state to Loading to show a progress indicator on the UI
            _categoryState.value = CategoryUiState.Loading
            val result = categoryUseCase.getCategoryById(id)
            _categoryState.value = when (result) {
                // If successful, wrap the single category in a list for the UI state.
                is Result.Success -> CategoryUiState.Success(result.data?.let { listOf(it) } ?: emptyList())
                is Result.Error -> CategoryUiState.Error("Failed to load category.")
                is Result.Loading -> CategoryUiState.Loading
            }
        }
    }
}
