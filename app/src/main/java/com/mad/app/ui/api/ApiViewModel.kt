package com.mad.app.ui.api

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mad.app.data.model.Post
import com.mad.app.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

sealed class ApiUiState {
    data object Loading : ApiUiState()
    data class Success(val posts: List<Post>) : ApiUiState()
    data class Error(val message: String) : ApiUiState()
}

class ApiViewModel : ViewModel() {

    private val repository = PostRepository()

    private val _uiState = MutableStateFlow<ApiUiState>(ApiUiState.Loading)
    val uiState: StateFlow<ApiUiState> = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10
    private val allPosts = mutableListOf<Post>()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = ApiUiState.Loading
            currentPage = 0
            allPosts.clear()

            val result = repository.getPostsPaginated(0, pageSize)
            result.fold(
                onSuccess = { posts ->
                    allPosts.addAll(posts)
                    _uiState.value = ApiUiState.Success(allPosts.toList())
                    currentPage = 1
                    Timber.d("Initial load: ${posts.size} posts")
                },
                onFailure = { error ->
                    _uiState.value = ApiUiState.Error(
                        error.message ?: "Failed to load posts"
                    )
                    Timber.e(error, "Failed to load posts")
                }
            )
        }
    }

    fun loadMore() {
        if (_uiState.value !is ApiUiState.Success) return

        viewModelScope.launch {
            val start = currentPage * pageSize
            val result = repository.getPostsPaginated(start, pageSize)
            result.fold(
                onSuccess = { posts ->
                    if (posts.isNotEmpty()) {
                        allPosts.addAll(posts)
                        _uiState.value = ApiUiState.Success(allPosts.toList())
                        currentPage++
                        Timber.d("Loaded more: ${posts.size} posts, total: ${allPosts.size}")
                    }
                },
                onFailure = { error ->
                    Timber.w(error, "Failed to load more posts")
                }
            )
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            currentPage = 0
            allPosts.clear()

            val result = repository.getPostsPaginated(0, pageSize)
            result.fold(
                onSuccess = { posts ->
                    allPosts.addAll(posts)
                    _uiState.value = ApiUiState.Success(allPosts.toList())
                    currentPage = 1
                },
                onFailure = { error ->
                    _uiState.value = ApiUiState.Error(
                        error.message ?: "Failed to refresh"
                    )
                }
            )
            _isRefreshing.value = false
        }
    }
}
