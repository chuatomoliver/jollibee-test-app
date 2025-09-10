package com.certicode.jolibee_test_app.screens.contacts_people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.domain.peopleUseCase.AddPeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.DeletePeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.EditPeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.GetPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A sealed interface representing the different states of the contacts UI.
 * This helps manage the UI based on data loading, success, and error.
 */

/**
 * ViewModel for the ContactsPeople screen.
 * Handles the business logic and state management for fetching, adding, editing, and deleting contacts.
 *
 * @param addPeopleUseCase The use case for adding a new person.
 * @param deletePeopleUseCase The use case for deleting a person.
 * @param editPeopleUseCase The use case for editing an existing person.
 * @param getPeopleUseCase The use case for fetching the list of all people.
 */
@HiltViewModel
class ContactsPeopleViewModel @Inject constructor(
    private val addPeopleUseCase: AddPeopleUseCase,
    private val deletePeopleUseCase: DeletePeopleUseCase,
    private val editPeopleUseCase: EditPeopleUseCase,
    private val getPeopleUseCase: GetPeopleUseCase
) : ViewModel() {

    // The mutable state flow that holds the current UI state.
    private val _uiState = MutableStateFlow<ContactsPeopleUiState>(ContactsPeopleUiState.Loading)
    // The public, read-only state flow that UI components can observe.
    val uiState: StateFlow<ContactsPeopleUiState> = _uiState

    init {
        // Fetch the initial list of people when the ViewModel is created.
        getPeople()
    }

    /**
     * Fetches the list of all people from the repository and updates the UI state.
     */
    fun getPeople() {
        viewModelScope.launch {
            getPeopleUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> ContactsPeopleUiState.Success(result.data)
                    is Result.Loading -> ContactsPeopleUiState.Loading
                    is Result.Error -> ContactsPeopleUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    /**
     * Adds a new person to the repository.
     * @param person The [PersonModel] to be added.
     */
    fun addPerson(people: PeopleModel) {
        viewModelScope.launch {
            _uiState.value = ContactsPeopleUiState.Loading
            when (val result = addPeopleUseCase(people)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully added.
                    _uiState.value = ContactsPeopleUiState.ContactPeopleAdded
                }
                is Result.Loading -> _uiState.value = ContactsPeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    ContactsPeopleUiState.Error(result.exception.message ?: "Failed to add person")
            }
        }
    }

    /**
     * Deletes a person from the repository.
     * @param person The [PersonModel] to be deleted.
     */
    fun deletePerson(person: PeopleModel) {
        viewModelScope.launch {
            when (val result = deletePeopleUseCase(person)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully deleted.
                    _uiState.value = ContactsPeopleUiState.ContactPeopleDeleted
                }
                is Result.Loading -> _uiState.value = ContactsPeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    ContactsPeopleUiState.Error(result.exception.message ?: "Failed to delete person")
            }
        }
    }

    /**
     * Edits an existing person in the repository.
     * @param person The [PersonModel] with updated information.
     */
    fun editPerson(person: PeopleModel) {
        viewModelScope.launch {
            when (val result = editPeopleUseCase(person)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully edited.
                    _uiState.value = ContactsPeopleUiState.ContactPeopleUpdated
                }
                is Result.Loading -> _uiState.value = ContactsPeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    ContactsPeopleUiState.Error(result.exception.message ?: "Failed to edit person")
            }
        }
    }

    /**
     * Resets the state after a person has been added, triggering a UI refresh.
     */
    fun resetPersonAddedState() {
        getPeople()
    }
}
