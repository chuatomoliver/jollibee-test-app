package com.certicode.jolibee_test_app.data.jollibeedata.repository

import com.certicode.jolibee_test_app.Result

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleDao
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(private val peopleDao: PeopleDao) {

    fun getAllPeople(): Flow<Result<List<PeopleModel>>> = flow {
        emit(Result.Loading)
        try {
            peopleDao.getAllPeople().collect { people ->
                emit(Result.Success(people))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    /**
     * Inserts a new business into the database, returning a Result.
     */
    suspend fun insertPeople(people: PeopleModel): Result<Unit> {
        return try {
            peopleDao.insert(people)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Deletes a business from the database, returning a Result.
     */
    suspend fun deletePeople(people: PeopleModel): Result<Unit> {
        return try {
            peopleDao.delete(people)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Retrieves a single business by its ID, returning a Result.
     */
    suspend fun getPeopleById(id: Long): Result<PeopleModel?> {
        return try {
            val people = peopleDao.getPersonById(id)
            Result.Success(people)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * Updates an existing business in the database, returning a Result.
     */
    suspend fun updatePeople(people: PeopleModel): Result<Unit> {
        return try {
            peopleDao.update(people)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}