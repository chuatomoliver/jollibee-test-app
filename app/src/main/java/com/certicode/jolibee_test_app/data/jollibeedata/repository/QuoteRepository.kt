package com.certicode.jolibee_test_app.testdata.repository

import com.certicode.jolibee_test_app.testdata.database.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.certicode.jolibee_test_app.testdata.database.QuoteDao
import com.certicode.jolibee_test_app.Result

class QuoteRepository @Inject constructor(private val quoteDao: QuoteDao) {
    fun getAllQuotes(): Flow<Result<List<Quote>>> = flow {
        emit(Result.Loading)
        try {
            quoteDao.getAll().collect { quotes ->
                emit(Result.Success(quotes))
            }
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    suspend fun addQuote(quote: Quote): Result<Unit> {
        return try {
            quoteDao.addQuote(quote)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deleteQuote(quote: Quote): Result<Unit> {
        return try {
            quoteDao.deleteQuote(quote)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}