package com.certicode.jolibee_test_app.domain.peopleUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.business.BusinessModel
import com.certicode.jolibee_test_app.data.jollibeedata.people.PeopleModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.BusinessRepository
import com.certicode.jolibee_test_app.data.jollibeedata.repository.PeopleRepository
import javax.inject.Inject


class AddPeopleUseCase @Inject constructor(private val repository: PeopleRepository) {
    suspend operator fun invoke(people: PeopleModel): Result<Unit> = repository.insertPeople(people)
}