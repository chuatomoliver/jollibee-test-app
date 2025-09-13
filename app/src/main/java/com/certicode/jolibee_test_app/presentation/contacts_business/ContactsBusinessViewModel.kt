package com.certicode.jolibee_test_app.presentation.contacts_business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel

import com.certicode.jolibee_test_app.domain.businessUseCase.DeleteBusinessUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.EditBusinessUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetBusinessByIdUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetBusinessUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetCategoriesUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetTagsUseCase
import com.certicode.jolibee_test_app.domain.usecases.AddBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the ContactsBusiness screen.
 * This class handles the UI logic and data fetching for businesses, categories, and tags.
 */
@HiltViewModel
class ContactsBusinessViewModel @Inject constructor(
    private val getBusinessUseCase: GetBusinessUseCase,
    private val addBusinessUseCase: AddBusinessUseCase,
    private val deleteBusinessUseCase: DeleteBusinessUseCase,
    private val editBusinessUseCase: EditBusinessUseCase,
    private val getBusinessByIdUseCase: GetBusinessByIdUseCase,
    // Injecting the new use cases
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {

    // State for the list of businesses
    private val _uiState = MutableStateFlow<BusinessUiState>(BusinessUiState.Loading)
    val uiState: StateFlow<BusinessUiState> = _uiState.asStateFlow()

    // State for the list of categories
    private val _categoriesUiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val categoriesUiState: StateFlow<CategoryUiState> = _categoriesUiState.asStateFlow()

    // State for the list of tags
    private val _tagsUiState = MutableStateFlow<TagsUiState>(TagsUiState.Loading)
    val tagsUiState: StateFlow<TagsUiState> = _tagsUiState.asStateFlow()

    init {
        getBusinesses()
        getCategories()
        getTags()
    }

    /**
     * Fetches all businesses from the database and updates the UI state.
     */
    fun getBusinesses() {
        viewModelScope.launch {
            getBusinessUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> BusinessUiState.Success(result.data)
                    is Result.Loading -> BusinessUiState.Loading
                    is Result.Error -> BusinessUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    /**
     * Fetches all categories from the database and updates the UI state.
     */
    fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect { result ->
                _categoriesUiState.value = when (result) {
                    is Result.Success -> CategoryUiState.Success(result.data)
                    is Result.Loading -> CategoryUiState.Loading
                    is Result.Error -> CategoryUiState.Error(
                        result.exception.message ?: "Failed to load categories"
                    )
                }
            }
        }
    }

    /**
     * Fetches all tags from the database and updates the UI state.
     */
    fun getTags() {
        viewModelScope.launch {
            getTagsUseCase().collect { result ->
                _tagsUiState.value = when (result) {
                    is Result.Success -> TagsUiState.Success(result.data)
                    is Result.Loading -> TagsUiState.Loading
                    is Result.Error -> TagsUiState.Error(
                        result.exception.message ?: "Failed to load tags"
                    )
                }
            }
        }
    }

    // Other functions remain the same
    fun resetBusinessUpdatedState() {
        _uiState.value = BusinessUiState.Success(emptyList())
    }

    fun addBusiness(business: BusinessModel) {
        viewModelScope.launch {
            _uiState.value = BusinessUiState.Loading
            when (val result = addBusinessUseCase(business)) {
                is Result.Success -> {
                    _uiState.value = BusinessUiState.ContactBusinessAdded
                }
                is Result.Loading -> _uiState.value = BusinessUiState.Loading
                is Result.Error -> _uiState.value =
                    BusinessUiState.Error(result.exception.message ?: "Failed to add business")
            }
        }
    }

    fun deleteBusiness(business: BusinessModel) {
        viewModelScope.launch {
            when (val result = deleteBusinessUseCase(business)) {
                is Result.Success -> {
                    _uiState.value = BusinessUiState.ContactBusinessDeleted
                }
                is Result.Loading -> _uiState.value = BusinessUiState.Loading
                is Result.Error -> _uiState.value =
                    BusinessUiState.Error(result.exception.message ?: "Failed to delete business")
            }
        }
    }

    fun editBusiness(business: BusinessModel) {
        viewModelScope.launch {
            when (val result = editBusinessUseCase(business)) {
                is Result.Success -> {
                    _uiState.value = BusinessUiState.ContactBusinessUpdated
                }
                is Result.Loading -> _uiState.value = BusinessUiState.Loading
                is Result.Error -> _uiState.value =
                    BusinessUiState.Error(result.exception.message ?: "Failed to edit business")
            }
        }
    }

    fun resetBusinessAddedState() {
        _uiState.value = BusinessUiState.Success(emptyList())
    }

    fun resetBusinessState() {
        getBusinesses()
    }

    fun getBusinessById(businessId: Long) {
        viewModelScope.launch {
            _uiState.value = BusinessUiState.Loading
            when (val result = getBusinessByIdUseCase(businessId)) {
                is Result.Success -> {
                    val business = result.data
                    if (business != null) {
                        _uiState.value = BusinessUiState.BusinessLoaded(business)
                    } else {
                        _uiState.value = BusinessUiState.Error("Business not found.")
                    }
                }
                is Result.Error -> {
                    _uiState.value = BusinessUiState.Error(
                        result.exception.message ?: "Failed to load business."
                    )
                }
                is Result.Loading -> {
                    _uiState.value = BusinessUiState.Loading
                }
            }
        }
    }
}

// UI State sealed classes for categories and tags
sealed class CategoryUiState {
    object Loading : CategoryUiState()
    data class Success(val categories: List<CategoryModel>) : CategoryUiState()
    data class Error(val message: String) : CategoryUiState()
}

sealed class TagsUiState {
    object Loading : TagsUiState()
    data class Success(val tags: List<TagsModel>) : TagsUiState()
    data class Error(val message: String) : TagsUiState()
}
