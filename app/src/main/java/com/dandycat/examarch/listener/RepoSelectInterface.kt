package com.dandycat.examarch.listener

import com.dandycat.domain.model.RepositoryEntity

interface RepoSelectInterface {
    fun selectRepoModel(entity : RepositoryEntity)
}