package com.muinv.businesscard.data.remote

import com.muinv.businesscard.data.remote.param.RequestParam
import com.muinv.businesscard.data.remote.response.ParamResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("SelectUser.php")
    fun userLogin(@Body param: RequestParam): Observable<ParamResponse>

    @POST("allData.php")
    fun allData(@Body param: RequestParam): Observable<ParamResponse>

    @POST("create.php")
    fun create(@Body param: RequestParam): Observable<ParamResponse>

    @POST("update.php")
    fun update(@Body param: RequestParam): Observable<ParamResponse>

    @POST("delete.php")
    fun delete(@Body param: RequestParam): Observable<ParamResponse>

    @POST("getImage.php")
    fun getImage(@Body param: RequestParam): Observable<ParamResponse>
}