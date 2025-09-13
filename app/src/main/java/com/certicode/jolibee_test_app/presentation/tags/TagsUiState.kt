package com.certicode.jolibee_test_app.presentation.tags

import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel


sealed interface TagsUiState {
    object Loading : TagsUiState
    data class Success(val tags: List<TagsModel>) : TagsUiState
    data class Error(val message: String) : TagsUiState
    object TagsAdded : TagsUiState
    object TagsUpdated : TagsUiState
    object TagsDeleted : TagsUiState
}