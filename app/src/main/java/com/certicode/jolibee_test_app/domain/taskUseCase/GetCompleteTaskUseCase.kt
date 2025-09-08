package com.certicode.jolibee_test_app.domain.taskUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.repository.TaskRepository
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(): Flow<Result<List<TaskModel>>> = repository.getAllCompletedTasks()
}