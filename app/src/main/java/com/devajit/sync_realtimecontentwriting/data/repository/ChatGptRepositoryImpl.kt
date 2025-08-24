package com.devajit.sync_realtimecontentwriting.data.repository

import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.gptbot.models.ChoiceBody
import com.devajit.gptbot.models.GptBody
import com.devajit.gptbot.models.GptBodyDto
import com.devajit.sync_realtimecontentwriting.data.remote.ApiRoutes
import com.devajit.sync_realtimecontentwriting.domain.repository.IChatGptRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class ChatGptRepositoryImpl @Inject constructor(
    private val apiImpl : ApiRoutes
) : IChatGptRepo {
    override fun getGptSuggestion(gptBody: GptBody): Flow<Resources<List<ChoiceBody>>> = flow {
        emit(Resources.Loading(data = listOf()))
        val result = apiImpl.sendMessageToGpt(gptBody = GptBodyDto.fromGptBody(gptBody.messages, gptBody.model))

        try {
            if (result.choices.isNotEmpty()) {
                emit(Resources.Success(data= result.choices))
            } else {
                emit(Resources.Error(data = listOf(), message = "There was an error!"))
            }
        }
        catch (e : Exception) {
            e.printStackTrace()
            emit(Resources.Error(data = listOf(), message = e.message))
        }
    }
}