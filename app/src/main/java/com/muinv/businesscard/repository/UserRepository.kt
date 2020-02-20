package com.muinv.businesscard.repository

import com.muinv.businesscard.data.local.user.UserDao
import com.muinv.businesscard.data.remote.ApiService
import com.muinv.businesscard.data.remote.data.User
import com.muinv.businesscard.data.remote.param.LoginParam
import com.muinv.businesscard.repository.mapper.UserMapper
import io.reactivex.Observable

class UserRepository(apiService: ApiService, val userDao: UserDao, val userMapper: UserMapper) :
    BaseRepository(apiService) {
    fun userLogin(loginParam: LoginParam): Observable<User> {
        return requestApi(ApiAddress.USER_LOGIN, loginParam, {
            userDao.insertUser(userMapper.fromDataToObject(it))
        })
    }

    companion object {

        private var INSTANCE: UserRepository? = null

        @JvmStatic fun getInstance(apiService: ApiService, userDao: UserDao, userMapper: UserMapper) =
            INSTANCE ?: synchronized(UserRepository::class.java) {
                INSTANCE ?: UserRepository(apiService, userDao, userMapper)
                    .also { INSTANCE = it }
            }

        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}