package com.certicode.jolibee_test_app.domain.businessUseCase

import com.certicode.jolibee_test_app.data.jollibeedata.categories.CategoryModel
import com.certicode.jolibee_test_app.data.jollibeedata.repository.BusinessRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.certicode.jolibee_test_app.Result
import com.certicode.jolibee_test_app.data.jollibeedata.tags.TagsModel

class GetTagsUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {
    operator fun invoke(): Flow<Result<List<TagsModel>>> {
        return businessRepository.getTags()
    }
}