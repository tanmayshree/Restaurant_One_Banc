package com.tanmay.restaurant_one_banc.Utils

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://uat.onebanc.ai/"
    private const val API_KEY = "uonebancservceemultrS3cg8RaL30"

    private fun createHttpClient(action: String): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val newRequest = originalRequest.newBuilder()
                .addHeader("X-Partner-API-Key", API_KEY)
                .addHeader("X-Forward-Proxy-Action", action)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun apiService(action: String): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient(action))
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
