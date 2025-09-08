package com.certicode.jolibee_test_app.screens.ui_task_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.certicode.jolibee_test_app.domain.taskUseCase.AddTaskUseCase
import com.certicode.jolibee_test_app.domain.taskUseCase.DeleteTaskUseCase
import com.certicode.jolibee_test_app.domain.taskUseCase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase().collect { result ->
                _uiState.value = when (result) {
                    is Result.Success -> TaskUiState.Success(result.data)
                    is Result.Loading -> TaskUiState.Loading
                    is Result.Error -> TaskUiState.Error(
                        result.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun addTask(task: TaskModel) {
        viewModelScope.launch {
            when (val result = addTaskUseCase(task)) {
                is Result.Success -> { /* No-op, the flow from getTasks() will handle the update */ }
                is Result.Loading -> _uiState.value = TaskUiState.Loading
                is Result.Error -> _uiState.value =
                    TaskUiState.Error(result.exception.message ?: "Failed to add task")
            }
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {
            when (val result = deleteTaskUseCase(task)) {
                is Result.Success -> { /* No-op, the flow from getTasks() will handle the update */ }
                is Result.Loading -> _uiState.value = TaskUiState.Loading
                is Result.Error -> _uiState.value =
                    TaskUiState.Error(result.exception.message ?: "Failed to delete task")
            }
        }
    }
}