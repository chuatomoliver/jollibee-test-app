package com.certicode.jolibee_test_app.data.jollibeedata.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.domain.tagsUseCase.TagsUseCase
import com.certicode.jolibee_test_app.presentation.tags.TagsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsViewModel @Inject constructor(
    private val tagsUseCase: TagsUseCase
) : ViewModel() {

    // A private mutable state flow to hold the list of tags and their UI state.
    private val _tagsState = MutableStateFlow<TagsUiState>(TagsUiState.Loading)
    // A public immutable state flow for the UI to observe.
    val tagsState: StateFlow<TagsUiState> = _tagsState.asStateFlow()

    init {
        // Fetch the initial list of tags when the ViewModel is created.
        getAllTags()
    }

    /**
     * Resets the tags state to the initial loading state.
     * This is useful for preventing transient states from persisting on screen.
     */
    fun resetTagsState() {
        _tagsState.value = TagsUiState.Loading
    }

    fun getAllTags(){
        viewModelScope.launch {
            tagsUseCase.getAllTags().collect { result ->
                _tagsState.value = when (result) {
                    is Result.Loading -> TagsUiState.Loading
                    is Result.Success -> TagsUiState.Success(result.data)
                    is Result.Error -> TagsUiState.Error("Failed to load tags.")
                }
            }
        }
    }

    fun resetTagsListState() {
        getAllTags()
    }


    /**
     * Adds a new tag to the database.
     * @param tag The [TagsModel] to be added.
     */
    fun addTag(tag: TagsModel) {
        viewModelScope.launch {
            val result = tagsUseCase.addTag(tag)
            _tagsState.value = when (result) {
                is Result.Success -> TagsUiState.TagsAdded
                is Result.Error -> TagsUiState.Error("Failed to add tag.")
                is Result.Loading -> TagsUiState.Loading // This state is for the repository, but we include it for completeness.
            }
        }
    }

    /**
     * Deletes a tag from the database.
     * @param tag The [TagsModel] to be deleted.
     */
    fun deleteTag(tag: TagsModel) {
        viewModelScope.launch {
            val result = tagsUseCase.deleteTag(tag)
            _tagsState.value = when (result) {
                is Result.Success -> TagsUiState.TagsDeleted
                is Result.Error -> TagsUiState.Error("Failed to delete tag.")
                is Result.Loading -> TagsUiState.Loading
            }
        }
    }

    /**
     * Updates an existing tag in the database.
     * @param tag The [TagsModel] to be updated.
     */
    fun updateTag(tag: TagsModel) {
        viewModelScope.launch {
            val result = tagsUseCase.updateTag(tag)
            _tagsState.value = when (result) {
                is Result.Success -> TagsUiState.TagsUpdated
                is Result.Error -> TagsUiState.Error("Failed to update tag.")
                is Result.Loading -> TagsUiState.Loading
            }
        }
    }

    /**
     * Retrieves a single tag by its ID and updates the state.
     * @param id The ID of the tag to retrieve.
     */
    fun getTagById(id: Long) {
        viewModelScope.launch {
            _tagsState.value = TagsUiState.Loading
            val result = tagsUseCase.getTagById(id)
            _tagsState.value = when (result) {
                is Result.Success -> TagsUiState.Success(result.data?.let { listOf(it) } ?: emptyList())
                is Result.Error -> TagsUiState.Error("Failed to load tag.")
                is Result.Loading -> TagsUiState.Loading
            }
        }
    }
}
