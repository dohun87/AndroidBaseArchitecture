package com.dandycat.domain.repo

import com.dandycat.domain.model.SearchRepositoryEntity
import com.dandycat.domain.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun searchGitHubRepository(query : String) : Flow<ApiResult<SearchRepositoryEntity>>
}