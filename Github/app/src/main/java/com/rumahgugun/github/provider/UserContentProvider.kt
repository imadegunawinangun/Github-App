package com.rumahgugun.github.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.rumahgugun.github.data.UserDetailDao
import com.rumahgugun.github.data.local.UserDatabase

class UserContentProvider : ContentProvider() {

    companion object {
        const val AUTHOITY = "com.rumahgugun.github"
        const val TABLE_NAME = "user_detail"
        const val ID_FAVORITE_USER_DATA = 1
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    }

    init {
        uriMatcher.addURI(AUTHOITY, TABLE_NAME, ID_FAVORITE_USER_DATA)
    }

    private lateinit var userDetailDao: UserDetailDao

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
return null    }

    override fun onCreate(): Boolean {
        userDetailDao = context?.let {
            UserDatabase.getDatabase(it)?.userDetailDao()
        }!!
    return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor:Cursor?
        when(uriMatcher.match(uri)){
            ID_FAVORITE_USER_DATA -> {
                cursor = userDetailDao.findAll()
                if (context!=null){
                    cursor.setNotificationUri(context?.contentResolver,uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
return 0    }
}