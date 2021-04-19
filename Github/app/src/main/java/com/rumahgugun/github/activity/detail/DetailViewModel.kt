package com.rumahgugun.github.activity.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rumahgugun.github.api.RetrofitClient
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserDetailDao
import com.rumahgugun.github.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<UserDetail>()

    private var userDetailDao: UserDetailDao?
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDetailDao = userDatabase?.userDetailDao()
    }


    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetail> {
                override fun onResponse(
                    call: Call<UserDetail>,
                    response: Response<UserDetail>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getUserDetail(): LiveData<UserDetail> {
        return user
    }

    fun addToFavorite(userDetail: UserDetail){
        CoroutineScope(Dispatchers.IO).launch {
            userDetailDao?.addToFavorite(userDetail)
        }
    }
    suspend fun checkUser(id: Int) = userDetailDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDetailDao?.removeFromFavorite(id)
        }
    }
}