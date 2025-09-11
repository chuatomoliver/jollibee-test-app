package com.certicode.jolibee_test_app.data.jollibeedata.repository

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsDao
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagsRepository @Inject constructor(private val tagsDao: TagsDao) {

    fun getAllTags(): Flow<Result<List<TagsModel>>> = flow {
        emit(Result.Loading)
        try {
            tagsDao.getAllTags().collect { tags ->
                emit(Result.Success(tags))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    /**
     * Inserts a new tag into the database, returning a Result.
     */
    suspend fun insertTag(tag: TagsModel): Result<Unit> {
        return try {
            tagsDao.insert(tag)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Deletes a tag from the database, returning a Result.
     */
    suspend fun deleteTag(tag: TagsModel): Result<Unit> {
        return try {
            tagsDao.delete(tag)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Retrieves a single tag by its ID, returning a Result.
     */
    suspend fun getTagById(id: Long): Result<TagsModel?> {
        return try {
            val tag = tagsDao.getTagById(id)
            Result.Success(tag)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Updates an existing tag in the database, returning a Result.
     */
    suspend fun updateTag(tag: TagsModel): Result<Unit> {
        return try {
            tagsDao.update(tag)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}