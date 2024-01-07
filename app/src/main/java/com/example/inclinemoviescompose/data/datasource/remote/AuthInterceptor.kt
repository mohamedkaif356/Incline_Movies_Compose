package com.example.inclinemoviescompose.data.datasource.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val provider: PropertyProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val apiKey = provider.getProperty("API_KEY")

        if (apiKey.isNotBlank()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
        }

        return chain.proceed(request)
    }
}