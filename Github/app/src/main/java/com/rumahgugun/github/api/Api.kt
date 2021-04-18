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
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getSearchUsers(
            @Query("q") query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getUserDetail(
            @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getFollowers(
            @Path("username") username: String
    ): Call<ArrayList<UserDetail>>

    @GET("users/{username}/following")
    @Headers("Authorization: BuildConfig.GITHUB_TOKEN")
    fun getFollowing(
            @Path("username") username: String
    ): Call<ArrayList<UserDetail>>
}