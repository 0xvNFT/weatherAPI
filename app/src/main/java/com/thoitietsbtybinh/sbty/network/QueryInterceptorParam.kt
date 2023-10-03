package com.thoitietsbtybinh.sbty.network

import com.thoitietsbtybinh.sbty.WeatherSbty
import com.thoitietsbtybinh.sbty.utils.APP_ID
import com.thoitietsbtybinh.sbty.utils.PrefManager
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptorParam : Interceptor {

    val context = WeatherSbty.context
    private val prefManager = PrefManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url().newBuilder()
            .addQueryParameter("appid", APP_ID)
            .addQueryParameter("units", prefManager.tempUnit)
            .build()

        val request = chain.request().newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}