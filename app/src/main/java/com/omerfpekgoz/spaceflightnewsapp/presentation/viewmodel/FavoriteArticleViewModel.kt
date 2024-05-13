package com.omerfpekgoz.spaceflightnewsapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omerfpekgoz.spaceflightnewsapp.domain.model.FavoriteArticle
import com.omerfpekgoz.spaceflightnewsapp.domain.use_case.DeleteArticleUseCase
import com.omerfpekgoz.spaceflightnewsapp.domain.use_case.GetArticleUseCase
import com.omerfpekgoz.spaceflightnewsapp.domain.use_case.InsertArticleUseCase
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
class FavoriteArticleViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val insertArticleUseCase: InsertArticleUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase
) : ViewModel() {

    private var _favoriteArticleState = MutableLiveData<Resource<*>>()
    val favoriteArticleState = _favoriteArticleState

    private var _insertFavoriteArticleState = MutableLiveData<Resource<*>>()
    val insertFavoriteArticleState = _insertFavoriteArticleState


    init {
        getFavoriteArticleList()
    }

    private fun getFavoriteArticleList() {
        viewModelScope.launch {
            getArticleUseCase.getFavoriteArticleList().onEach {
                when (it) {
                    is Resource.Loading -> {
                        _favoriteArticleState.value = Resource.Loading
                    }

                    is Resource.Success -> {
                        _favoriteArticleState.value = Resource.Success(it.data)
                    }

                    is Resource.Error -> {
                        _favoriteArticleState.value = Resource.Error(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticle) {
        viewModelScope.launch {
            insertArticleUseCase.insertFavoriteArticle(favoriteArticle).onEach {
                when (it) {
                    is Resource.Loading -> {
                        _insertFavoriteArticleState.value = Resource.Loading
                    }

                    is Resource.Success -> {
                        _insertFavoriteArticleState.value = Resource.Success(it.data)
                    }

                    is Resource.Error -> {
                        _insertFavoriteArticleState.value = Resource.Error(it.message)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    suspend fun isArticleInFavorites(id: Int): FavoriteArticle? {
        return getArticleUseCase.isArticleInFavorites(id)
    }

     fun deleteFavoriteArticle(id: Int) {
        deleteArticleUseCase.deleteFavoriteArticle(id)
    }
}