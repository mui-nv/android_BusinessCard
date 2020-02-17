package com.example.businesscard.repository

import android.util.Log
import com.example.businesscard.data.remote.ApiService
import com.example.businesscard.data.remote.param.RequestParam
import com.example.businesscard.data.remote.response.ParamResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.magro.myapplicationaes.AESCipher
import io.reactivex.Observable


open class BaseRepository(val apiService: ApiService) {

    inline fun <T, reified K> requestApi(
        apiAddress: ApiAddress, param: T,
        noinline onNextHandle: ((K) -> Unit)?
    ): Observable<K> {
        val paramJson = dataToString(param)
        val encodeJson = AESCipher.encryption(AESCipher.key, AESCipher.iv, paramJson)
        val requestParam = RequestParam()
        requestParam.param = encodeJson.toString()


        val apiResponse: Observable<ParamResponse>
        when (apiAddress) {
            ApiAddress.USER_LOGIN -> apiResponse = apiService.userLogin(requestParam)
            ApiAddress.CREATE_INFORMATION -> apiResponse = apiService.create(requestParam)
            ApiAddress.UPDATE_INFORMATION -> apiResponse = apiService.update(requestParam)
            ApiAddress.DELETE_INFORMATION -> apiResponse = apiService.delete(requestParam)
            ApiAddress.GET_IMAGE -> apiResponse = apiService.getImage(requestParam)
            else -> apiResponse = apiService.userLogin(requestParam)
        }

        return apiResponse
            .flatMap {
                val decodeString = AESCipher.decrypt(AESCipher.key, AESCipher.iv, it.param)
                var user = jsonToData<K>(decodeString.toString())
                return@flatMap Observable.create<K> {
                    it.onNext(user)
                }
            }
            .doOnNext {
                if (onNextHandle != null) {
                    onNextHandle(it!!)
                }
            }
    }

    inline fun <T, reified K : Any> requestApiList(
        apiAddress: ApiAddress, param: T,
        noinline onNextHandle: ((List<K>) -> Unit)?
    ): Observable<List<K>> {
        val gson = Gson()
        val paramJson = dataToString(param)
        val encodeJson = AESCipher.encryption(AESCipher.key, AESCipher.iv, paramJson)
        val requestParam = RequestParam()
        requestParam.param = encodeJson.toString()

        val apiResponse: Observable<ParamResponse>
        when (apiAddress) {
            ApiAddress.GET_ALL_INFORMATION -> apiResponse = apiService.allData(requestParam)
            else -> apiResponse = apiService.allData(requestParam)
        }

        return apiResponse
            .flatMap {
                val decodeString = AESCipher.decrypt(AESCipher.key, AESCipher.iv, it.param)

                val groupListType = object : TypeToken<ArrayList<K>>() {}.type
                val model = gson.fromJson<List<K>>(decodeString, groupListType)
                return@flatMap Observable.create<List<K>> {
                    it.onNext(model)
                }
            }
            .doOnNext {
                if (onNextHandle != null) {
                    onNextHandle(it!!)
                }
            }
    }

    inline fun <T, reified K> requestApi(
        apiAddress: ApiAddress, param: T
    ): Observable<K> {
        return requestApi(apiAddress, param, null)
    }

    fun <T> dataToString(data: T): String {
        return Gson().toJson(data)
    }

    inline fun <reified K> jsonToData(data: String): K {
        return Gson().fromJson(data, K::class.java)
    }
}

enum class ApiAddress {
    USER_LOGIN, GET_ALL_INFORMATION, CREATE_INFORMATION, UPDATE_INFORMATION, DELETE_INFORMATION,
    GET_IMAGE
}