package com.certicode.jolibee_test_app.data.jollibeedata.repository

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryDao
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepository @Inject constructor(private val categoryDao: CategoryDao) {

    /**
     * Retrieves all categories from the database.
     */
    fun getAllCategories(): Flow<Result<List<CategoryModel>>> = flow {
        emit(Result.Loading)
        try {
            categoryDao.getAllCategories().collect { categories ->
                emit(Result.Success(categories))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    /**
     * Inserts a new category into the database, returning a Result.
     */
    suspend fun insertCategory(category: CategoryModel): Result<Unit> {
        return try {
            categoryDao.insert(category)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Deletes a category from the database, returning a Result.
     */
    suspend fun deleteCategory(category: CategoryModel): Result<Unit> {
        return try {
            categoryDao.delete(category)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Retrieves a single category by its ID, returning a Result.
     */
    suspend fun getCategoryById(id: Long): Result<CategoryModel?> {
        return try {
            val category = categoryDao.getCategoryById(id)
            Result.Success(category)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Updates an existing category in the database, returning a Result.
     */
    suspend fun updateCategory(category: CategoryModel): Result<Unit> {
        return try {
            categoryDao.update(category)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}