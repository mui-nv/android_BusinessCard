package com.example.businesscard.data.remote.data

import com.google.gson.annotations.SerializedName

open class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    var user: String?,
    @SerializedName("password")
    var password: String?
)

open class Information(id: Int, company: String, department: String, address: String)