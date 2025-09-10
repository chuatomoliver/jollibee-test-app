package com.certicode.jolibee_test_app.screens.contacts_people

import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel

sealed interface PeopleUiState {
    object Loading : PeopleUiState
    data class Success(val people: List<PeopleModel>) : PeopleUiState
    data class PersonLoaded(val person: PeopleModel) : PeopleUiState // New state for a single person
    data class Error(val message: String) : PeopleUiState
    object ContactPeopleAdded : PeopleUiState
    object ContactPeopleUpdated : PeopleUiState
    object ContactPeopleDeleted : PeopleUiState
}