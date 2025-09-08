package com.certicode.jolibee_test_app.domain

import com.certicode.jolibee_test_app.testdata.database.Quote
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.certicode.jolibee_test_app.Result

import com.certicode.jolibee_test_app.testdata.repository.QuoteRepository

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {
    operator fun invoke(): Flow<Result<List<Quote>>> = repository.getAllQuotes()
}
