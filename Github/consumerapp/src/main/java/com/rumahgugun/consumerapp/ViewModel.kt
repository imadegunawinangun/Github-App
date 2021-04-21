package com.rumahgugun.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModel(application: Application): AndroidViewModel(application) {

    private var totalCount: Long? = null

    private val listUser = MutableLiveData<ArrayList<UserDetail>>()

    fun setListUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.UserColumns.CONTENT_URI, null, null, null, null
        )
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        listUser.postValue(listConverted)
        totalCount = listConverted.size.toLong()
    }

    fun getListUser(): LiveData<ArrayList<UserDetail>> {
        return listUser
    }

    fun getTotalUser(): Long? {
        return totalCount
    }


}