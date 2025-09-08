package com.certicode.jolibee_test_app.domain.usecases

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.BusinessRepository
import com.certicode.jolibee_test_app.Result
import javax.inject.Inject

class AddBusinessUseCase @Inject constructor(private val repository: BusinessRepository) {
    suspend operator fun invoke(business: BusinessModel): Result<Unit> = repository.insertBusiness(business)
}