package com.certicode.jolibee_test_app.presentation.contacts_business

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.presentation.contacts_people.PeopleUiState

interface BusinessUiState {
    object Loading : BusinessUiState
    data class Success(val business: List<BusinessModel>) : BusinessUiState
    data class BusinessLoaded(val business: BusinessModel) : BusinessUiState // New state for a single person
    data class Error(val message: String) : BusinessUiState
    object ContactBusinessAdded : BusinessUiState
    object ContactBusinessUpdated : BusinessUiState
    object ContactBusinessDeleted : BusinessUiState
    object Idle : BusinessUiState
}