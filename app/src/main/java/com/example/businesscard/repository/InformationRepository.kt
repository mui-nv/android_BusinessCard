package com.example.businesscard.repository

import com.example.businesscard.data.local.user.UserDao
import com.example.businesscard.data.remote.ApiService
import com.example.businesscard.data.remote.data.Information
import com.example.businesscard.data.remote.param.BaseParam
import com.example.businesscard.data.remote.param.DeleteParam
import com.example.businesscard.data.remote.response.SuccessResponse
import com.example.businesscard.repository.mapper.InformationMapper
import io.reactivex.Observable

class InformationRepository(
    apiService: ApiService, val userDao: UserDao,
    val informationMapper: InformationMapper
) : BaseRepository(apiService) {

    fun allData(): Observable<List<Information>> {
        var getUserId = Observable.create<Int> {
            val userID = userDao.getUsers().first().id
            it.onNext(userID)
        }

        return getUserId.flatMap {
            var param = BaseParam(it)
            return@flatMap requestApiList<BaseParam, Information>(ApiAddress.GET_ALL_INFORMATION, param, null)
        }
    }

    fun create(information: Information): Observable<Information> {
        val userID = userDao.getUsers().first().id
        information.userID = userID

        return requestApi(ApiAddress.CREATE_INFORMATION, information)
    }

    fun update(information: Information): Observable<SuccessResponse> {
        val userID = userDao.getUsers().first().id
        information.userID = userID

        return requestApi(ApiAddress.UPDATE_INFORMATION, information)
    }

    fun delete(id: Int): Observable<SuccessResponse> {
        val userID = userDao.getUsers().first().id
        var param = DeleteParam(id, userID)

        return requestApi(ApiAddress.DELETE_INFORMATION, param)
    }

    companion object {

        private var INSTANCE: InformationRepository? = null

        @JvmStatic
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao,
            informationMapper: InformationMapper
        ) =
            INSTANCE ?: synchronized(UserRepository::class.java) {
                INSTANCE ?: InformationRepository(apiService, userDao, informationMapper)
                    .also { INSTANCE = it }
            }

        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}