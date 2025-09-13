package com.certicode.jolibee_test_app.data.jollibeedata.business

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the BusinessModel entity.
 * This interface defines the methods for interacting with the 'businesses' table.
 */
@Dao
interface BusinessDao {

    // Inserts a new business into the database.
    // If a conflict occurs (e.g., a duplicate primary key), it will be replaced.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(business: BusinessModel)

    // Updates an existing business in the database.
    @Update
    suspend fun update(business: BusinessModel)

    // Deletes a business from the database.
    @Delete
    suspend fun delete(business: BusinessModel)

    // Retrieves all businesses from the database, ordered by business name.
    // Returns a Flow, which emits new data whenever the table changes.
    @Query("SELECT * FROM businesses ORDER BY id DESC")
    fun getAllBusinesses(): Flow<List<BusinessModel>>

    // Retrieves a single business by its ID.
    @Query("SELECT * FROM businesses WHERE id = :id")
    suspend fun getBusinessById(id: Long): BusinessModel?

    @Query("SELECT * FROM categories ORDER BY id DESC")
    fun getAllCategories(): Flow<List<CategoryModel>>

    @Query("SELECT * FROM tags ORDER BY id DESC")
    fun getTagsName(): Flow<List<TagsModel>>
}