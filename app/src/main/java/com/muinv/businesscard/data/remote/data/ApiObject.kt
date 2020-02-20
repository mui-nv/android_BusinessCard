package com.muinv.businesscard.data.remote.data

import com.google.gson.annotations.SerializedName

open class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user")
    var user: String?,
    @SerializedName("password")
    var password: String?
)

open class Information(
    @SerializedName("id") var id: Int,
    @SerializedName("userID") var userID: Int,
    @SerializedName("name1") var name1: String,
    @SerializedName("name2") var name2: String,
    @SerializedName("company") var company: String?,
    @SerializedName("postal") var postal: String?,
    @SerializedName("department") var department: String?,
    @SerializedName("address1") var address1: String?,
    @SerializedName("address2") var address2: String?,
    @SerializedName("latitude") var latitude: Double?,
    @SerializedName("longitude") var longitude: Double?,
    @SerializedName("image") var image: String?,
    @SerializedName("image_base64") var image_base64: String?
)