package com.example.businesscard.data.remote

import com.example.businesscard.data.remote.response.ParamResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("3/movie/{movie_id}/credits")
    suspend fun userLogin(@Path("movie_id") movieId: String): ParamResponse
}