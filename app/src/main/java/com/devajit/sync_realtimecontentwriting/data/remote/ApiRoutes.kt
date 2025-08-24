package com.devajit.sync_realtimecontentwriting.data.remote

import android.content.Context
import com.devajit.gptbot.models.GptBodyDto
import com.devajit.gptbot.models.MessageResponseDto
import com.devajit.sync_realtimecontentwriting.R
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRoutes {

   @POST("completions")
   suspend fun sendMessageToGpt(@Body gptBody: GptBodyDto) : MessageResponseDto
   companion object {
      fun BASE_URL(context: Context): String {
         return context.getString(R.string.GPT_BASE_URl)
      }
      fun API_KEY(context: Context): String {
         return context.getString(R.string.GPT_API_KEY)
      }
   }
}