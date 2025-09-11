package com.certicode.jolibee_test_app.presentation.tasks

import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Success(val tasks: List<TaskModel>) : TaskUiState
    data class Error(val message: String) : TaskUiState
    object TaskAdded : TaskUiState
}