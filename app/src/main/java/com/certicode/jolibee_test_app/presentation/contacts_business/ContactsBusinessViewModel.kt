package com.certicode.jolibee_test_app.presentation.contacts_business

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel

import com.certicode.jolibee_test_app.domain.businessUseCase.DeleteBusinessUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.EditBusinessUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetBusinessByIdUseCase
import com.certicode.jolibee_test_app.domain.businessUseCase.GetBusinessUseCase
import com.certicode.jolibee_test_app.domain.usecases.AddBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsBusinessViewModel @Inject constructor(
    private val getBusinessUseCase: GetBusinessUseCase,
    private val addBusinessUseCase: AddBusinessUseCase,
    private val deleteBusinessUseCase: DeleteBusinessUseCase,
    private val editBusinessUseCase: EditBusinessUseCase,
    private val getBusinessByIdUseCase: GetBusinessByIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<BusinessUiState>(BusinessUiState.Loading)
    val uiState: StateFlow<BusinessUiState> = _uiState.asStateFlow()

    init {
        getBusinesses()
    }

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

    fun resetBusinessUpdatedState() {
        // Set the state back to a neutral state, such as Loading or Success.
        // This prevents the LaunchedEffect in the UI from continuously observing
        // a "business updated" state and re-running the side effect.
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
