package com.example.businesscard.data.remote

import com.example.businesscard.data.remote.param.RequestParam
import com.example.businesscard.data.remote.response.ParamResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("SelectUser.php")
    fun userLogin(@Body param: RequestParam): Observable<ParamResponse>
}