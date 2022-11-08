package com.dandycat.data.repo

import com.dandycat.data.model.mapperSearchRepositoryEntity
import com.dandycat.domain.repo.SearchRepository
import com.dandycat.domain.util.ApiResult
import com.dandycat.domain.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val service : GitHubService) : SearchRepository {

    override fun searchGitHubRepository(query: String,page : Int) = flow{
        val response = service.searchGithubRepository(query,page)
        if(response.isSuccessful){
            response.body()?.let {
                emit(ApiResult.Success(it.mapperSearchRepositoryEntity()))
            }
        }else{
            response.errorBody()?.let {
                emit(ApiResult.Fail(response.code(), it.string()))
            }
        }
    }.catch { exception ->
        if(exception is HttpException){
            emit(ApiResult.Error(exception.code(),exception))
        }else{
            emit(ApiResult.Error(exception = exception))
        }
    }
}