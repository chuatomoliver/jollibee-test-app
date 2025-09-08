package com.certicode.jolibee_test_app.screens.ui_task_add

import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

sealed interface TaskUiState {
    object Loading : TaskUiState
    data class Success(val tasks: List<TaskModel>) : TaskUiState
    data class Error(val message: String) : TaskUiState
}