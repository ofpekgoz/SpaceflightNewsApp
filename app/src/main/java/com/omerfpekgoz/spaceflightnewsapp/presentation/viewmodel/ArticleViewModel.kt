package com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omerfpekgoz.spaceflightnewsapp.domain.use_case.GetArticleUseCase
import com.omerfpekgoz.spaceflightnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by omerfarukpekgoz on 11.05.2024.
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {

    private var _articleState = MutableLiveData<Resource<*>>()
    val articleState = _articleState

    fun getArticleList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            getArticleUseCase.getArticleList(forceFetchFromRemote).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _articleState.value = Resource.Loading
                    }

                    is Resource.Success -> {
                        _articleState.value = Resource.Success(it.data)
                    }

                    is Resource.Error -> {
                        _articleState.value = Resource.Error(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}
