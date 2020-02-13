package com.example.businesscard.repository

import com.example.businesscard.data.remote.ApiService
import com.example.businesscard.data.remote.param.RequestParam
import com.example.businesscard.data.remote.response.ParamResponse
import com.google.gson.Gson
import com.magro.myapplicationaes.AESCipher
import io.reactivex.Observable

open class BaseRepository(val apiService: ApiService) {

    inline fun <T, reified V> requestApi(
        apiAddress: ApiAddress, param: T,
        noinline onNextHandle: ((V) -> Unit)?
    ): Observable<V> {
        val paramJson = dataToString(param)
        val encodeJson = AESCipher.encryption(AESCipher.key, AESCipher.iv, paramJson)
        val param = RequestParam()
        param.param = encodeJson.toString()


        val apiResponse: Observable<ParamResponse>
        when (apiAddress) {
            ApiAddress.USER_LOGIN -> apiResponse = apiService.userLogin(param)
            else -> apiResponse = apiService.userLogin(param)
        }

        return apiResponse
            .flatMap({
                val decodeString = AESCipher.decrypt(AESCipher.key, AESCipher.iv, it.param)
                var user = jsonToData<V>(decodeString.toString())
                return@flatMap Observable.create<V> {
                    it.onNext(user)
                }
            })
            .doOnNext {
                if (onNextHandle != null) {
                    onNextHandle(it!!)
                }
            }
    }

    inline fun <T, reified V> requestApi(
        apiAddress: ApiAddress, param: T
    ): Observable<V> {
        return requestApi(apiAddress, param, null)
    }

    fun <T> dataToString(data: T): String {
        return Gson().toJson(data)
    }

    inline fun <reified T> jsonToData(data: String): T {
        return Gson().fromJson(data, T::class.java)
    }
}

enum class ApiAddress {
    USER_LOGIN, GET_ALL_INFORMATION, CREATE_INFORMATION
}