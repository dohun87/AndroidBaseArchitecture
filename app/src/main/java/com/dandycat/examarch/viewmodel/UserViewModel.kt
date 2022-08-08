package com.dandycat.examarch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandycat.domain.model.RepositoryEntity
import com.dandycat.domain.model.SearchRepositoryEntity
import com.dandycat.domain.usecase.SearchUseCase
import com.dandycat.domain.util.ApiResult
import com.dandycat.domain.util.Logger
import com.dandycat.examarch.module.ToastModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val searchUseCase : SearchUseCase,
    private val toastModule : ToastModule
) : ViewModel() {

    val query = MutableLiveData<String>("")
    private val _searchList = MutableLiveData<List<RepositoryEntity>>()
    val searchList : LiveData<List<RepositoryEntity>> get() = _searchList

    private val _isLoading = MutableLiveData<Boolean>(false) // 초기값 선언
    val isLoading : LiveData<Boolean> get() = _isLoading

    private val _notSearch = MutableLiveData<Boolean>(false) // 초기값 선언
    val notSearch : LiveData<Boolean> get() = _notSearch

    fun getUserInfo(){
        val keyword = query.value
        if (!keyword.isNullOrEmpty()) {
            viewModelScope.launch {
                Logger.d("execute Search - keyword : $keyword")
                searchUseCase.executeRepositorySearch(keyword)
                    .onStart { _isLoading.postValue(true) } // 로딩 시작
                    .onCompletion { _isLoading.postValue(false) } // 로딩 종료
                    .collect {
                    when(it){
                        is ApiResult.Success -> {
                            Logger.d("ApiResult Success - listSize : ${it.value.repositories.size}")
                            //_searchItem.postValue(it.value)
                            _notSearch.postValue(it.value.totalCount==0)
                            _searchList.postValue(it.value.repositories)
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
    }
}