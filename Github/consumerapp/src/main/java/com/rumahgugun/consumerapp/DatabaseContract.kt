package com.rumahgugun.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.rumahgugun.github"
    const val SCHEME = "content"
    internal class UserColumns: BaseColumns{
        companion object{
            private const val TABLE_NAME = "user_detail"
            const val ID = "id"
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}