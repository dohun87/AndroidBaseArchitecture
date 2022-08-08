package com.dandycat.data.repo

import com.dandycat.data.model.SearchRepositoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("search/repositories")
    suspend fun searchGithubRepository(
        @Query("q") query : String,
        @Query("page") page : Int = 1,
        @Query("per_page") per_page : Int = 30,
        @Query("sort") sort : String = "stars",
        @Query("order") order : String = "desc",
    ) : Response<SearchRepositoryResponse>
}