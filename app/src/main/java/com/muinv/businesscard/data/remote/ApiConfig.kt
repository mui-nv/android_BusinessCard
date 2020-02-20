package com.muinv.businesscard.data.remote

import com.muinv.businesscard.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    fun createLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    fun createHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.run {
                url.newBuilder().build()
            }
            val newRequest = request.newBuilder()
                .url(newUrl)
                .method(request.method, request.body)
                .build()
            chain.proceed(newRequest)
        }

    fun createOkHttpClient(
        logging: Interceptor,
        header: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15.toLong(), TimeUnit.SECONDS)
            .readTimeout(15.toLong(), TimeUnit.SECONDS)
            .addInterceptor(header)
            .addInterceptor(logging)
            .build()

    fun createAppRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("http://10.160.36.7/BusinessCard/")
            .client(okHttpClient)
            .build()


    fun createApiService(): ApiService {
        val interceptor = createLoggingInterceptor()
        val header = createHeaderInterceptor()
        val okHttpClient = createOkHttpClient(interceptor, header)
        val retrofit = createAppRetrofit(okHttpClient)
        return retrofit.create(ApiService::class.java)
    }
}