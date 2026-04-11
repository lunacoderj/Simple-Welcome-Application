package com.mad.app.data.remote

import com.mad.app.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts")
    suspend fun getPostsPaginated(
        @Query("_start") start: Int,
        @Query("_limit") limit: Int
    ): List<Post>
}
