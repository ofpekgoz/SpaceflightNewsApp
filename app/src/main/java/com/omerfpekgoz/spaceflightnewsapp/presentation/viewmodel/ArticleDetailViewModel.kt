package com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel

import android.util.Log
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
class ArticleDetailViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase
) : ViewModel() {

    private var _articleDetailState = MutableLiveData<Resource<*>>()
    val articleDetailState = _articleDetailState

    fun getArticleById(id: Int, forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            getArticleUseCase.getArticleById(id, forceFetchFromRemote).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _articleDetailState.value = Resource.Loading
                    }

                    is Resource.Success -> {
                        _articleDetailState.value = Resource.Success(it.data)
                    }

                    is Resource.Error -> {
                        _articleDetailState.value = Resource.Error(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}