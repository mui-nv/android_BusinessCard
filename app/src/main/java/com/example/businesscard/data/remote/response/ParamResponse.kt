package com.example.businesscard.data.remote.response

import com.google.gson.annotations.SerializedName

class ParamResponse {
    @SerializedName("param")
    lateinit var param: String
}