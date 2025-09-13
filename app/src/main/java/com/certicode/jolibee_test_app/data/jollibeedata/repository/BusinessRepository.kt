package com.certicode.jolibee_test_app.data.jollibeedata.repository

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessDao
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BusinessRepository @Inject constructor(private val businessDao: BusinessDao) {

    fun getAllBusinesses(): Flow<Result<List<BusinessModel>>> = flow {
        emit(Result.Loading)
        try {
            businessDao.getAllBusinesses().collect { businesses ->
                emit(Result.Success(businesses))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    // Corrected function to get all categories
    fun getAllCategories(): Flow<Result<List<CategoryModel>>> = flow {
        emit(Result.Loading)
        try {
            businessDao.getAllCategories().collect { categories ->
                emit(Result.Success(categories))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    /**
     * Inserts a new business into the database, returning a Result.
     */
    suspend fun insertBusiness(business: BusinessModel): Result<Unit> {
        return try {
            businessDao.insert(business)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Deletes a business from the database, returning a Result.
     */
    suspend fun deleteBusiness(business: BusinessModel): Result<Unit> {
        return try {
            businessDao.delete(business)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Retrieves a single business by its ID, returning a Result.
     */
    suspend fun getBusinessById(id: Long): Result<BusinessModel?> {
        return try {
            val business = businessDao.getBusinessById(id)
            Result.Success(business)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Updates an existing business in the database, returning a Result.
     */
    suspend fun updateBusiness(business: BusinessModel): Result<Unit> {
        return try {
            businessDao.update(business)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    // New functions added below this line

    // Fetches a list of all tags from the database
    fun getTags(): Flow<Result<List<TagsModel>>> = flow {
        emit(Result.Loading)
        try {
            businessDao.getTagsName().collect { tags ->
                emit(Result.Success(tags))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}