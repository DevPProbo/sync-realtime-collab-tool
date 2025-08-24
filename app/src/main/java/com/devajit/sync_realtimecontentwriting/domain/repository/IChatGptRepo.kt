package com.devajit.sync_realtimecontentwriting.domain.repository

import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.gptbot.models.ChoiceBody
import com.devajit.gptbot.models.GptBody
import kotlinx.coroutines.flow.Flow

interface IChatGptRepo {
    fun getGptSuggestion(gptBody: GptBody) : Flow<Resources<List<ChoiceBody>>>
}