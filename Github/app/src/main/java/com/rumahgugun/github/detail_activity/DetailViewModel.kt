package com.rumahgugun.github.detail_activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rumahgugun.github.api.RetrofitClient
import com.rumahgugun.github.data.UserDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    val user = MutableLiveData<UserDetail>()

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

}