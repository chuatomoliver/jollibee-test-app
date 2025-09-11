package com.certicode.jolibee_test_app.presentation.tasks

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
import com.certicode.jolibee_test_app.domain.taskUseCase.EditTaskUseCase
import com.certicode.jolibee_test_app.domain.taskUseCase.GetCompleteTaskUseCase
import com.certicode.jolibee_test_app.domain.taskUseCase.GetOpenTaskUseCase

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val getOpenTaskUseCase: GetOpenTaskUseCase,
    private val getCompleteTaskUseCase: GetCompleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<TaskUiState>(TaskUiState.Loading)
    val uiState: StateFlow<TaskUiState> = _uiState

    init {
        getOpenTasks()
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

    private fun getOpenTasks() {
        viewModelScope.launch {
            getOpenTaskUseCase().collect { result ->
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

    private fun getCompleteTasks() {
        viewModelScope.launch {
            getCompleteTaskUseCase().collect { result ->
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
            _uiState.value = TaskUiState.Loading // Optional: Show a loading state while adding
            when (val result = addTaskUseCase(task)) {
                is Result.Success -> {
                    // This is the crucial change.
                    // Only set the state to TaskAdded and do nothing else.
                    _uiState.value = TaskUiState.TaskAdded
                }
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

    fun editTask(task: TaskModel) {
        viewModelScope.launch {
            when (val result = editTaskUseCase(task)) {
                is Result.Success -> { /* No-op, the flow from getTasks() will handle the update */ }
                is Result.Loading -> _uiState.value = TaskUiState.Loading
                is Result.Error -> _uiState.value =
                    TaskUiState.Error(result.exception.message ?: "Failed to edit task")
            }
        }
    }
    fun resetTaskOpenState(){
        getOpenTasks()
    }

    fun resetTaskCompleteState(){
        getCompleteTasks()
    }

    // The reset function is now responsible for fetching the new data
    fun resetTaskAddedState() {
        // This will trigger the getTasks() function, which will
        // change the state from TaskAdded to Success and update the UI.
        getTasks()
    }

    fun filterTasks(filterType: String) {
        when (filterType) {
            "open" -> getOpenTasks()
            "complete" -> getCompleteTasks()
        }
    }
}