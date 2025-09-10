package com.certicode.jolibee_test_app.domain.businessUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetBusinessUseCase @Inject constructor(private val repository: BusinessRepository) {
    operator fun invoke(): Flow<Result<List<BusinessModel>>> = repository.getAllBusinesses()
}