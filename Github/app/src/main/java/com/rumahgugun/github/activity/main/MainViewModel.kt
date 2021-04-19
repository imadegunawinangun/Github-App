package com.rumahgugun.github.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rumahgugun.github.api.RetrofitClient
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<UserDetail>>()
    private var totalCount: Long? = null

    fun setSearchUser(query: String) {
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                        totalCount = (response.body()?.total_count)
                    }
                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<UserDetail>> {
        return listUser
    }

    fun getTotalUser(): Long? {
        return totalCount
    }
}