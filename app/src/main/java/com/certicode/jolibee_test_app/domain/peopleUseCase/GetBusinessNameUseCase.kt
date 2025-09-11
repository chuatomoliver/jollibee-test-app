package com.certicode.jolibee_test_app.domain.peopleUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import com.certicode.jolibee_test_app.Result
import javax.inject.Inject


class GetBusinessNameUseCase @Inject constructor(private val repository: PeopleRepository) {
    operator fun invoke(): Flow<Result<List<BusinessModel>>> = repository.getAllBusinessName()
}