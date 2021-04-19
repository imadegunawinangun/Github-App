package com.rumahgugun.github.activity.listfavorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserDetailDao
import com.rumahgugun.github.data.local.UserDatabase

class ListFavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDetailDao: UserDetailDao?
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDetailDao = userDatabase?.userDetailDao()
    }

    fun getFavoriteUser(): LiveData<List<UserDetail>>?{
        return userDetailDao?.getFavoriteUser()
    }


}