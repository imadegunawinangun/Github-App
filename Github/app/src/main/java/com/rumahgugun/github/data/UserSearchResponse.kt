package com.rumahgugun.github.data

data class UserSearchResponse(
        val items : ArrayList<UserDetail>,
        val total_count: Long
)
