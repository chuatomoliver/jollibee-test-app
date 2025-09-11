package com.certicode.jolibee_test_app.screens.tags

import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel


sealed interface TagUiState {
    object Loading : TagUiState
    data class Success(val tasks: List<TagsModel>) : TagUiState
    data class Error(val message: String) : TagUiState
    object TaskAdded : TagUiState
}