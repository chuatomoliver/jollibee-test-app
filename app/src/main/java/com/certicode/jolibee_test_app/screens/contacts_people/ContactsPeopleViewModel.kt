package com.certicode.jolibee_test_app.screens.contacts_people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.domain.peopleUseCase.AddPeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.DeletePeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.EditPeopleUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.GetPeopleByIdUseCase
import com.certicode.jolibee_test_app.domain.peopleUseCase.GetPeopleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ContactsPeopleViewModel @Inject constructor(
    private val addPeopleUseCase: AddPeopleUseCase,
    private val deletePeopleUseCase: DeletePeopleUseCase,
    private val editPeopleUseCase: EditPeopleUseCase,
    private val getPeopleUseCase: GetPeopleUseCase,
    private val getPeopleByIdUseCase: GetPeopleByIdUseCase // Inject the new use case
) : ViewModel() {

    // The mutable state flow that holds the current UI state.
    private val _uiState = MutableStateFlow<PeopleUiState>(PeopleUiState.Loading)
    // The public, read-only state flow that UI components can observe.
    val uiState: StateFlow<PeopleUiState> = _uiState.asStateFlow()

    init {
        // Fetch the initial list of people when the ViewModel is created.
        getPeople()
    }

    // Existing functions (getPeople, addPerson, deletePerson, editPerson, resetPersonAddedState)
    // remain the same.

    /**
     * Fetches the list of all people from the repository and updates the UI state.
     */
    fun getPeople() {
        viewModelScope.launch {
            getPeopleUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> PeopleUiState.Success(result.data)
                    is Result.Loading -> PeopleUiState.Loading
                    is Result.Error -> PeopleUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    /**
     * Adds a new person to the repository.
     * @param people The [PeopleModel] to be added.
     */
    fun addPerson(people: PeopleModel) {
        viewModelScope.launch {
            _uiState.value = PeopleUiState.Loading
            when (val result = addPeopleUseCase(people)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully added.
                    _uiState.value = PeopleUiState.ContactPeopleAdded
                }
                is Result.Loading -> _uiState.value = PeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    PeopleUiState.Error(result.exception.message ?: "Failed to add person")
            }
        }
    }

    /**
     * Deletes a person from the repository.
     * @param person The [PeopleModel] to be deleted.
     */
    fun deletePerson(person: PeopleModel) {
        viewModelScope.launch {
            when (val result = deletePeopleUseCase(person)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully deleted.
                    _uiState.value = PeopleUiState.ContactPeopleDeleted
                }
                is Result.Loading -> _uiState.value = PeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    PeopleUiState.Error(result.exception.message ?: "Failed to delete person")
            }
        }
    }

    /**
     * Edits an existing person in the repository.
     * @param person The [PeopleModel] with updated information.
     */
    fun editPerson(person: PeopleModel) {
        viewModelScope.launch {
            when (val result = editPeopleUseCase(person)) {
                is Result.Success -> {
                    // Update state to indicate the person was successfully edited.
                    _uiState.value = PeopleUiState.ContactPeopleUpdated
                }
                is Result.Loading -> _uiState.value = PeopleUiState.Loading
                is Result.Error -> _uiState.value =
                    PeopleUiState.Error(result.exception.message ?: "Failed to edit person")
            }
        }
    }

    /**
     * Resets the state after a person has been added, triggering a UI refresh.
     */
    fun resetPersonAddedState() {
        getPeople()
    }

    // New function to fetch a person by ID
    /**
     * Fetches a person by their unique ID.
     * @param personId The ID of the person to fetch.
     */
    fun getPersonById(peopleId: Long) {
        viewModelScope.launch {
            _uiState.value = PeopleUiState.Loading
            when (val result = getPeopleByIdUseCase(peopleId)) {
                is Result.Success -> {
                    val person = result.data
                    if (person != null) {
                        _uiState.value = PeopleUiState.PersonLoaded(person)
                    } else {
                        _uiState.value = PeopleUiState.Error("Person not found.")
                    }
                }
                is Result.Error -> {
                    _uiState.value = PeopleUiState.Error(
                        result.exception.message ?: "Failed to load person."
                    )
                }
                is Result.Loading -> {
                    _uiState.value = PeopleUiState.Loading
                }
            }
        }
    }
}