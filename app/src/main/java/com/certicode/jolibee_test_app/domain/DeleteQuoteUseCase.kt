package com.certicode.jolibee_test_app.domain

import com.certicode.jolibee_test_app.testdata.database.Quote
import javax.inject.Inject
import com.certicode.jolibee_test_app.testdata.repository.QuoteRepository
import com.certicode.jolibee_test_app.Result

class DeleteQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(quote: Quote): Result<Unit> = repository.deleteQuote(quote)
}