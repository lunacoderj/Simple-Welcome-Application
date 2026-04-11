package com.mad.app.data.repository

import com.mad.app.data.model.Post
import com.mad.app.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class PostRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getPosts(): Result<List<Post>> = withContext(Dispatchers.IO) {
        try {
            val posts = apiService.getPosts()
            Timber.d("Fetched ${posts.size} posts successfully")
            Result.success(posts)
        } catch (e: Exception) {
            Timber.e(e, "Failed to fetch posts")
            Result.failure(e)
        }
    }

    suspend fun getPostsPaginated(start: Int, limit: Int): Result<List<Post>> =
        withContext(Dispatchers.IO) {
            try {
                val posts = apiService.getPostsPaginated(start, limit)
                Timber.d("Fetched ${posts.size} posts (start=$start, limit=$limit)")
                Result.success(posts)
            } catch (e: Exception) {
                Timber.e(e, "Failed to fetch paginated posts")
                Result.failure(e)
            }
        }
}
