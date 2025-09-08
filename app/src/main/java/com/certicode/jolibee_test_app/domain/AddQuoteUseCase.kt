package com.certicode.jolibee_test_app.domain

import com.certicode.jolibee_test_app.testdata.database.Quote
import javax.inject.Inject
import com.certicode.jolibee_test_app.Result

import com.certicode.jolibee_test_app.testdata.repository.QuoteRepository

class AddQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(quote: Quote): Result<Unit> = repository.addQuote(quote)
}