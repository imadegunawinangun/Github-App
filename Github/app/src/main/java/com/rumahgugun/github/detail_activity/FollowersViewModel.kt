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

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<UserDetail>>()

    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<UserDetail>> {
                override fun onResponse(
                    call: Call<ArrayList<UserDetail>>,
                    response: Response<ArrayList<UserDetail>>
                ) {
                    if (response.isSuccessful) {
                        listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserDetail>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getListFollower(): LiveData<ArrayList<UserDetail>> {
        return listFollowers
    }

}