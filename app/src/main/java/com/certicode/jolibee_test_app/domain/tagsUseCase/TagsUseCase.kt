package com.certicode.jolibee_test_app.domain.tagsUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.repository.TagsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * A use case class that provides a clean API for the UI layer to interact with tag data.
 * This class abstracts the data source and business logic for tag-related operations.
 *
 * @param tagsRepository The repository responsible for fetching and manipulating tag data.
 */
class TagsUseCase @Inject constructor(
    private val tagsRepository: TagsRepository
) {

    /**
     * Inserts a new tag.
     * @param tag The [TagsModel] to be added.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun addTag(tag: TagsModel): Result<Unit> {
        return tagsRepository.insertTag(tag)
    }

    /**
     * Deletes a tag.
     * @param tag The [TagsModel] to be deleted.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun deleteTag(tag: TagsModel): Result<Unit> {
        return tagsRepository.deleteTag(tag)
    }

    /**
     * Updates an existing tag.
     * @param tag The [TagsModel] to be updated.
     * @return A [Result] object indicating the success or failure of the operation.
     */
    suspend fun updateTag(tag: TagsModel): Result<Unit> {
        return tagsRepository.updateTag(tag)
    }

    /**
     * Retrieves all tags from the database.
     * @return A [Flow] of [Result] containing a list of [TagsModel] objects.
     */
    fun getAllTags(): Flow<Result<List<TagsModel>>> {
        return tagsRepository.getAllTags()
    }

    /**
     * Retrieves a single tag by its ID.
     * @param id The ID of the tag to retrieve.
     * @return A [Result] object containing the found [TagsModel] or null if not found.
     */
    suspend fun getTagById(id: Long): Result<TagsModel?> {
        return tagsRepository.getTagById(id)
    }
}
