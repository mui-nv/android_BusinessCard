package com.example.businesscard.data.remote

import android.content.Context
import com.example.businesscard.BuildConfig
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    fun createOkHttpCache(context: Context): Cache =
        Cache(context.cacheDir, (10 * 1024 * 1024).toLong())

    fun createLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

    fun createHeaderInterceptor(): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            val newUrl = request.url().newBuilder()
                .build()
            val newRequest = request.newBuilder()
                .url(newUrl)
                .method(request.method(), request.body())
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
            .baseUrl("http://localhost/BusinessCard/")
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