package com.certicode.jolibee_test_app.screens.contacts_business

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel

sealed interface ContactsBusinessUiState {
    object Loading : ContactsBusinessUiState
    data class Success(val tasks: List<BusinessModel>) : ContactsBusinessUiState
    data class Error(val message: String) : ContactsBusinessUiState
    object ContactBusinessAdded : ContactsBusinessUiState
}