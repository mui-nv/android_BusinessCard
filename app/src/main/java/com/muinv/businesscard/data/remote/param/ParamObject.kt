package com.muinv.businesscard.data.remote.param

import com.google.gson.annotations.SerializedName

open class RequestParam {
    @SerializedName("param") lateinit var param: String
}