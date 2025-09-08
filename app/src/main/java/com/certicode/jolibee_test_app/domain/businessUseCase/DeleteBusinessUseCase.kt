package com.certicode.jolibee_test_app.domain.businessUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.repository.BusinessRepository
import javax.inject.Inject

class DeleteBusinessUseCase @Inject constructor(private val repository: BusinessRepository) {
    suspend operator fun invoke(business: BusinessModel): Result<Unit> = repository.deleteBusiness(business)
}