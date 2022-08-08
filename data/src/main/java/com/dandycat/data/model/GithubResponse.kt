package com.dandycat.data.model

import com.google.gson.annotations.SerializedName

data class SearchRepositoryResponse(
    @SerializedName("total_count") val totalCount : Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val repositories: List<RepositoryResponse>
)

data class RepositoryResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("owner") val owner: OwnerResponse,
    @SerializedName("private") val private: Boolean,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String,
    @SerializedName("forks") val forks: Long,
    @SerializedName("stargazers_count") val stargazersCount: Long
)

data class OwnerResponse (
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String
)