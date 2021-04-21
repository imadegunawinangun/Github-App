package com.rumahgugun.consumerapp

import android.database.Cursor

object MappingHelper {

    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<UserDetail> {
        val list = ArrayList<UserDetail>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.ID))
                val username =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.USERNAME))
                val avatarUrl =
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.UserColumns.AVATAR_URL))
                list.add(
                    UserDetail(
                        username,
                        id,
                        null,
                        avatarUrl,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                )
            }
        }
        return list
    }
}

