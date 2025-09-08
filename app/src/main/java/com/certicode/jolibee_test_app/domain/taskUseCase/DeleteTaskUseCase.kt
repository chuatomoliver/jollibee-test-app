package com.certicode.jolibee_test_app.domain.taskUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.repository.TaskRepository
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskModel): Result<Unit> = repository.deleteTask(task)
}