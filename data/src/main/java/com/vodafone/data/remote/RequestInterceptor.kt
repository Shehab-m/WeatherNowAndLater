package com.vodafone.data.remote

import com.vodafone.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return makeRequest(chain)
    }

    private fun makeRequest(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .addHeader(ACCEPT, ACCEPT_VALUE)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val ACCEPT_VALUE = "application/json"
        private const val API_KEY = "appid"
    }
}