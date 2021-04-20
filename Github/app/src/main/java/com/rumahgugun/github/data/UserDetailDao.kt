package com.rumahgugun.github.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDetailDao {
    @Insert
    suspend fun addToFavorite(userDetail: UserDetail)

    @Query("SELECT * FROM user_detail")
    fun getFavoriteUser(): LiveData<List<UserDetail>>

    @Query("SELECT count(*) FROM user_detail WHERE user_detail.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM user_detail WHERE user_detail.id = :id")
    suspend fun removeFromFavorite(id: Int): Int

    @Query("SELECT * FROM user_detail")
    fun findAll(): Cursor
}