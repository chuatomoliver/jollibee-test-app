package com.certicode.jolibee_test_app.data.jollibeedata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the CategoryModel entity.
 * This interface defines the methods for interacting with the 'categories' table.
 */
@Dao
interface CategoryDao {

    // Inserts a new category into the database.
    // If a conflict occurs (e.g., a duplicate primary key), it will be replaced.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(category: CategoryModel)

    // Updates an existing category in the database.
    @Update
    suspend fun update(category: CategoryModel)

    // Deletes a category from the database.
    @Delete
    suspend fun delete(category: CategoryModel)

    // Retrieves all categories from the database, ordered by category name.
    // Returns a Flow, which emits new data whenever the table changes.
    @Query("SELECT * FROM categories ORDER BY category_name ASC")
    fun getAllCategories(): Flow<List<CategoryModel>>

    // Retrieves a single category by its ID.
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): CategoryModel?
}