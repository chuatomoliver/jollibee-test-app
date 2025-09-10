package com.certicode.jolibee_test_app.screens.contacts_people

import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

sealed interface ContactsPeopleUiState {
    object Loading : ContactsPeopleUiState
    data class Success(val people: List<PeopleModel>) : ContactsPeopleUiState
    data class Error(val message: String) : ContactsPeopleUiState
    object ContactPeopleAdded : ContactsPeopleUiState
    object ContactPeopleUpdated : ContactsPeopleUiState // New State
    object ContactPeopleDeleted : ContactsPeopleUiState // New State
}