package com.certicode.jolibee_test_app.domain.categoryUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.CategoryRepository
import jakarta.inject.Inject
import com.certicode.jolibee_test_app.Result
import kotlinx.coroutines.flow.Flow

class CategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    /**
     * Inserts a new category.
     * @param category The [CategoryModel] to be added.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun addCategory(category: CategoryModel): Result<Unit> {
        return categoryRepository.insertCategory(category)
    }

    /**
     * Deletes a category.
     * @param category The [CategoryModel] to be deleted.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun deleteCategory(category: CategoryModel): Result<Unit> {
        return categoryRepository.deleteCategory(category)
    }

    /**
     * Updates an existing category.
     * @param category The [CategoryModel] to be updated.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun updateCategory(category: CategoryModel): Result<Unit> {
        return categoryRepository.updateCategory(category)
    }

    /**
     * Retrieves all categories from the database.
     * @return A [Flow] of [Result] containing a list of [CategoryModel] objects.
     */
    fun getAllCategories(): Flow<Result<List<CategoryModel>>> {
        return categoryRepository.getAllCategories()
    }

    /**
     * Retrieves a single category by its ID.
     * @param id The ID of the category to retrieve.
     * @return A [Result] object containing the found [CategoryModel] or null if not found.
     */
    suspend fun getCategoryById(id: Long): Result<CategoryModel?> {
        return categoryRepository.getCategoryById(id)
    }
}