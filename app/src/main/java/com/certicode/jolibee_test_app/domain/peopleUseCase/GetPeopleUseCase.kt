package com.certicode.jolibee_test_app.domain.peopleUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPeopleUseCase @Inject constructor(private val repository: PeopleRepository) {
    operator fun invoke(): Flow<Result<List<PeopleModel>>> = repository.getAllPeople()
}