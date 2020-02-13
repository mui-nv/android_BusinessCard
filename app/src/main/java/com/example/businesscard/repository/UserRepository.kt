package com.example.businesscard.repository

import com.example.businesscard.data.remote.ApiService
import com.example.businesscard.data.remote.data.User
import com.example.businesscard.data.remote.param.LoginParam
import io.reactivex.Observable

class UserRepository(apiService: ApiService) : BaseRepository(apiService) {
    fun userLogin(loginParam: LoginParam): Observable<User> {
        return requestApi(ApiAddress.USER_LOGIN, loginParam)
    }
}