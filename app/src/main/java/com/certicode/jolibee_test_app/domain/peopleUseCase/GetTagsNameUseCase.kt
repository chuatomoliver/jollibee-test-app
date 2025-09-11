package com.certicode.jolibee_test_app.domain.peopleUseCase

import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.repository.PeopleRepository
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTagsNameUseCase @Inject constructor(private val repository: PeopleRepository) {
    operator fun invoke(): Flow<Result<List<TagsModel>>> = repository.getTagsName()
}