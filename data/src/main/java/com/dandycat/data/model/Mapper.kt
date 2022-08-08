package com.dandycat.data.model

import com.dandycat.domain.model.OwnerEntity
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.domain.model.SearchRepositoryEntity

fun SearchRepositoryResponse.mapperSearchRepositoryEntity() : SearchRepositoryEntity
    = SearchRepositoryEntity(
        totalCount,incompleteResults, mapperRepositoryEntity(repositories)
    )

fun mapperRepositoryEntity(list : List<RepositoryResponse>) =
    list.toList().map {
        RepositoryEntity(
            it.id,
            it.name,
            it.owner.mapperOwnerEntity(),
            it.private,
            it.description,
            it.url,
            it.forks,
            it.stargazersCount
        )
    }

fun OwnerResponse.mapperOwnerEntity() = OwnerEntity(login,avatarUrl)
