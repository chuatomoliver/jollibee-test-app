package com.certicode.jolibee_test_app.domain.peopleUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.PeopleRepository
import javax.inject.Inject

class GetPeopleByIdUseCase @Inject constructor(private val repository: PeopleRepository) {
    suspend operator fun invoke(id: Long): Result<PeopleModel?> = repository.getPeopleById(id)
}