package com.rumahgugun.github.other

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rumahgugun.github.api.RetrofitClient
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserDetailDao
import com.rumahgugun.github.data.UserSearchResponse
import com.rumahgugun.github.data.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel(application: Application): AndroidViewModel(application) {

    private var totalCount: Long? = null

    private val user = MutableLiveData<UserDetail>()
    private val listUser = MutableLiveData<ArrayList<UserDetail>>()

    private var userDetailDao: UserDetailDao?
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDetailDao = userDatabase?.userDetailDao()
    }

    fun setListUser(query: String) {
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
    fun setListFollowers(username: String) {
        RetrofitClient.apiInstance
            .getFollowers(username)
            .enqueue(object : Callback<ArrayList<UserDetail>> {
                override fun onResponse(
                    call: Call<ArrayList<UserDetail>>,
                    response: Response<ArrayList<UserDetail>>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserDetail>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }
    fun setListFollowing(username: String) {
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<UserDetail>> {
                override fun onResponse(
                    call: Call<ArrayList<UserDetail>>,
                    response: Response<ArrayList<UserDetail>>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserDetail>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                }
            })
    }

    fun getListUser(): LiveData<ArrayList<UserDetail>> {
        return listUser
    }
    fun getUserDetail(): LiveData<UserDetail> {
        return user
    }
    fun getTotalUser(): Long? {
        return totalCount
    }

    fun getFavoriteUser(): LiveData<List<UserDetail>>?{
        return userDetailDao?.getFavoriteUser()
    }
    fun addToFavorite(userDetail: UserDetail){
        CoroutineScope(Dispatchers.IO).launch {
            userDetailDao?.addToFavorite(userDetail)
        }
    }
    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDetailDao?.removeFromFavorite(id)
        }
    }
    suspend fun checkUser(id: Int) = userDetailDao?.checkUser(id)

}