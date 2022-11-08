package com.dandycat.examarch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.domain.usecase.SearchUseCase
import com.dandycat.domain.util.ApiResult
import com.dandycat.domain.util.Logger
import com.dandycat.examarch.module.ToastModule
import com.dandycat.examarch.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase : SearchUseCase,
    private val toastModule : ToastModule
) : ViewModel() {

    private var page = 1
    private var searchKeyword = ""
    val query = MutableLiveData<String>("")
    private val _searchList = MutableLiveData<List<RepositoryEntity>>()
    val searchList : LiveData<List<RepositoryEntity>> get() = _searchList

    private val resultData = mutableListOf<RepositoryEntity>()

    private val _isLoading = MutableLiveData<Boolean>() // 초기값 선언
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _notSearch = MutableLiveData<Boolean>() // 초기값 선언
    val notSearch : LiveData<Boolean> get() = _notSearch

    private val _setRepoModel = MutableLiveData<Event<Boolean>>()
    val setRepoModel : LiveData<Event<Boolean>> get() = _setRepoModel

    private lateinit var mSelectRepoModel : RepositoryEntity

    fun checkInputKeyword(){
        viewModelScope.launch {
            query.value?.let {
                if(!it.equals(searchKeyword,true)){
                    resultData.clear()
                    _searchList.postValue(resultData.toList())
                    searchKeyword = it
                    page = 1
                }
                searchGithubRepo()
            } ?: kotlin.run {
                toastModule.showToast("검색어를 입력하세요")
            }
        }
    }

    fun addNextPage(){
        page +=1
        searchGithubRepo()
    }

    private fun searchGithubRepo(){
        viewModelScope.launch {
            Logger.d("execute Search - keyword : $searchKeyword / page : $page")
            searchUseCase.executeRepositorySearch(searchKeyword,page)
                .onStart { _isLoading.postValue(true) } // 로딩 시작
                .onCompletion { _isLoading.postValue(false) } // 로딩 종료
                .collect {
                    when(it){
                        is ApiResult.Success -> {
                            Logger.d("ApiResult Success - listSize : ${it.value.repositories.size}")
                            //_searchItem.postValue(it.value)
                            _notSearch.postValue(it.value.totalCount==0)
                            resultData.addAll(it.value.repositories)
                            _searchList.postValue(resultData.toList())
                        }
                        is ApiResult.Fail -> {
                            Logger.e("ApiResult Fail - code : ${it.code} / message : ${it.message}")
                            _notSearch.postValue(true)
                            toastModule.showToast("검새 결과가 없습니다.")
                        }
                        is ApiResult.Error -> {
                            Logger.e("ApiResult Error - code : ${it.code} / exception : ${it.exception} /" +
                                    " message : ${it.exception?.localizedMessage}")
                            toastModule.showToast("에러 발생!!")
                        }
                    }
                }
            }
        }

    fun setRepoModel(selectModel : RepositoryEntity){
        mSelectRepoModel = selectModel
        _setRepoModel.postValue(Event(true))
    }

    fun getRepoModel() = mSelectRepoModel
}