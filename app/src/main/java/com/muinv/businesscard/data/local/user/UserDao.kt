package com.muinv.businesscard.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muinv.businesscard.data.local.`object`.UserObject

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getUsers(): List<UserObject>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserObject)
}