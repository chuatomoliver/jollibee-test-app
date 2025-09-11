package com.certicode.jolibee_test_app.presentation.tags

import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel


sealed interface TagUiState {
    object Loading : TagUiState
    data class Success(val tasks: List<TagsModel>) : TagUiState
    data class Error(val message: String) : TagUiState
    object TaskAdded : TagUiState
}