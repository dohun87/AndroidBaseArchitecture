package com.dandycat.domain.usecase

import com.dandycat.domain.repo.SearchRepository

class SearchUseCase(private val repository: SearchRepository) {
    fun executeRepositorySearch(query : String) = repository.searchGitHubRepository(query)
}