package com.rumahgugun.github.api

import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
            @Query("q") query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
            @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
            @Path("username") username: String
    ): Call<ArrayList<UserDetail>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
            @Path("username") username: String
    ): Call<ArrayList<UserDetail>>
}