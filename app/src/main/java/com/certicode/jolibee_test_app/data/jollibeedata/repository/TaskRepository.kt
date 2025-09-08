package com.certicode.jolibee_test_app.data.jollibeedata.repository

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskModel
import com.certicode.jolibee_test_app.data.jollibeedata.tasks.TaskDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllTasks(): Flow<Result<List<TaskModel>>> = flow {
        emit(Result.Loading)
        try {
            taskDao.getAllTasks().collect { tasks ->
                emit(Result.Success(tasks))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    fun getAllCompletedTasks(): Flow<Result<List<TaskModel>>> = flow {
        emit(Result.Loading)
        try {
            taskDao.getAllTasks().collect { tasks ->
                emit(Result.Success(tasks))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    fun getAllOpenTasks(): Flow<Result<List<TaskModel>>> = flow {
        emit(Result.Loading)
        try {
            taskDao.getAllTasks().collect { tasks ->
                emit(Result.Success(tasks))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    /**
     * Inserts a new task into the database, returning a Result.
     */
    suspend fun insertTask(task: TaskModel): Result<Unit> {
        return try {
            taskDao.insert(task)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Deletes a task from the database, returning a Result.
     */
    suspend fun deleteTask(task: TaskModel): Result<Unit> {
        return try {
            taskDao.delete(task)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Retrieves a single task by its ID, returning a Result.
     */
    suspend fun getTaskById(id: Long): Result<TaskModel?> {
        return try {
            val task = taskDao.getTaskById(id)
            Result.Success(task)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Updates an existing task in the database, returning a Result.
     */
    suspend fun updateTask(task: TaskModel): Result<Unit> {
        return try {
            taskDao.update(task)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}