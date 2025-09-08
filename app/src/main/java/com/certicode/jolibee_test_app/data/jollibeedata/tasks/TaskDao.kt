package com.certicode.jolibee_test_app.data.jollibeedata.tasks

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the TaskModel entity.
 * This interface defines the methods for interacting with the 'tasks' table.
 */
@Dao
interface TaskDao {

    // Inserts a new task into the database.
    // If a conflict occurs (e.g., on the unique 'ship_id'), it will be replaced.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(task: TaskModel)

    // Updates an existing task in the database.
    @Update
    suspend fun update(task: TaskModel)

    // Deletes a task from the database.
    @Delete
    suspend fun delete(task: TaskModel)

    // Retrieves all tasks from the database, ordered by task name.
    // Returns a Flow, which emits new data whenever the table changes.
    @Query("SELECT * FROM tasks ORDER BY task_name ASC")
    fun getAllTasks(): Flow<List<TaskModel>>

    // Retrieves a single task by its ID.
    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskModel?
}