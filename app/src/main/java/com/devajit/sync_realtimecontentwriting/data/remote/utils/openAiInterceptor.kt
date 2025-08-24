package com.devajit.gptbot.network

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.devajit.sync_realtimecontentwriting.data.remote.ApiRoutes
import okhttp3.Interceptor
import okhttp3.Response

class OpenAiInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer ${ApiRoutes.API_KEY(context)}")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 429) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, "Too many requests. Try again later.", Toast.LENGTH_LONG).show()
            }
        }

        return response
    }
}