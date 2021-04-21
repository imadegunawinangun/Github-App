package com.rumahgugun.github.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rumahgugun.github.data.UserDetail
import com.rumahgugun.github.data.UserDetailDao

@Database(
        entities = [UserDetail::class],
        version = 1
)

abstract class UserDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE : UserDatabase? = null

        fun getDatabase (context: Context): UserDatabase?{
            if(INSTANCE==null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_detail").build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun userDetailDao(): UserDetailDao

}