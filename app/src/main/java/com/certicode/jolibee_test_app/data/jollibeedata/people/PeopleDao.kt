package com.certicode.jolibee_test_app.data.jollibeedata.people

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the PeopleModel entity.
 * This interface defines the methods for interacting with the 'people' table.
 */
@Dao
interface PeopleDao {

    // Inserts a new person into the database.
    // If a conflict occurs (e.g., a duplicate primary key), it will be replaced.
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(person: PeopleModel)

    // Updates an existing person in the database.
    @Update
    suspend fun update(person: PeopleModel)

    // Deletes a person from the database.
    @Delete
    suspend fun delete(person: PeopleModel)

    // Retrieves all people from the database, ordered by name.
    // Returns a Flow, which emits new data whenever the table changes.
    @Query("SELECT * FROM people ORDER BY id DESC")
    fun getAllPeople(): Flow<List<PeopleModel>>

    // Retrieves a single person by their ID.
    @Query("SELECT * FROM people WHERE id = :id")
    suspend fun getPersonById(id: Long): PeopleModel?

    @Query("SELECT * FROM people WHERE name LIKE '%' || :name || '%'")
    fun searchPeopleByName(name: String): Flow<List<PeopleModel>>

    @Query("SELECT * FROM businesses ORDER BY id DESC")
    fun getBusinessName(): Flow<List<BusinessModel>>

    @Query("SELECT * FROM tags ORDER BY id DESC")
    fun getTagsName(): Flow<List<TagsModel>>

}