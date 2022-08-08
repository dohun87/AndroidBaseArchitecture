package com.dandycat.domain.model

data class SearchRepositoryEntity(
    val totalCount : Int,
    val incompleteResults: Boolean,
    val repositories: List<RepositoryEntity>
)

data class RepositoryEntity(
    val id : Int,
    val name: String,
    val owner: OwnerEntity,
    val private: Boolean,
    val description: String?,
    val url: String,
    val forks: Long,
    val stargazersCount: Long
)

data class OwnerEntity (
    val login: String,
    val avatarUrl: String
)