package com.certicode.jolibee_test_app.data.jollibeedata.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the TagsModel entity.
 * This interface defines the methods for interacting with the 'tags' table.
 */
@Dao
interface TagsDao {

    // Inserts a new tag into the database.
    // If a conflict occurs (e.g., a duplicate primary key), it will be replaced.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(tag: TagsModel)

    // Updates an existing tag in the database.
    @Update
    suspend fun update(tag: TagsModel)

    // Deletes a tag from the database.
    @Delete
    suspend fun delete(tag: TagsModel)

    // Retrieves all tags from the database, ordered by tag name.
    // Returns a Flow, which emits new data whenever the table changes.
    @Query("SELECT * FROM tags ORDER BY tag_name ASC")
    fun getAllTags(): Flow<List<TagsModel>>

    // Retrieves a single tag by its ID.
    @Query("SELECT * FROM tags WHERE id = :id")
    suspend fun getTagById(id: Long): TagsModel?
}