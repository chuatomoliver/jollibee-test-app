package com.certicode.jolibee_test_app.domain.quoteUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.quote.Quote
import javax.inject.Inject
import com.certicode.jolibee_test_app.data.jollibeedata.repository.QuoteRepository
import com.certicode.jolibee_test_app.Result

class DeleteQuoteUseCase @Inject constructor(private val repository: QuoteRepository) {
    suspend operator fun invoke(quote: Quote): Result<Unit> = repository.deleteQuote(quote)
}