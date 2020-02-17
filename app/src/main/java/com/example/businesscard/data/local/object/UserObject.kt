package com.example.businesscard.data.local.`object`

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserObject @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "user") var user: String? = "",
    @ColumnInfo(name = "password") var password: String? = ""
)