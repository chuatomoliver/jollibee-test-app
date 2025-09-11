package com.certicode.jolibee_test_app.data.jollibeedata.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.domain.tagsUseCase.TagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A sealed interface representing the UI state for the Tags screen.
 * This provides a clear, exhaustive way to represent different states such as loading, success, and error.
 */
sealed interface TagUiState {
    object Loading : TagUiState
    data class Success(val tags: List<TagsModel>) : TagUiState
    data class Error(val message: String) : TagUiState
}

/**
 * ViewModel for the Tags feature.
 * This class is responsible for preparing and managing the data for the UI.
 * It uses a [TagsUseCase] to interact with the data layer and exposes the UI state
 * via a [StateFlow].
 */
@HiltViewModel
class TagsViewModel @Inject constructor(
    private val tagsUseCase: TagsUseCase
) : ViewModel() {

    // A private mutable state flow to hold the list of tags and their UI state.
    private val _tagsState = MutableStateFlow<TagUiState>(TagUiState.Loading)
    // A public immutable state flow for the UI to observe.
    val tagsState: StateFlow<TagUiState> = _tagsState.asStateFlow()

    init {
        // Collect all tags from the use case when the ViewModel is initialized.
        viewModelScope.launch {
            tagsUseCase.getAllTags().collect { result ->
                _tagsState.value = when (result) {
                    is Result.Loading -> TagUiState.Loading
                    is Result.Success -> TagUiState.Success(result.data)
                    is Result.Error -> TagUiState.Error("Failed to load tags.")
                }
            }
        }
    }

    /**
     * Adds a new tag to the database.
     * @param tag The [TagsModel] to be added.
     */
    fun addTag(tag: TagsModel) {
        viewModelScope.launch {
            val result = tagsUseCase.addTag(tag)
            if (result is Result.Error) {
                _tagsState.value = TagUiState.Error("Failed to add tag.")
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
            if (result is Result.Error) {
                _tagsState.value = TagUiState.Error("Failed to delete tag.")
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
            if (result is Result.Error) {
                _tagsState.value = TagUiState.Error("Failed to update tag.")
            }
        }
    }

    /**
     * Retrieves a single tag by its ID.
     * @param id The ID of the tag to retrieve.
     * @return A [Result] object containing the found [TagsModel] or null if not found.
     */
    suspend fun getTagById(id: Long): Result<TagsModel?> {
        return tagsUseCase.getTagById(id)
    }
}
