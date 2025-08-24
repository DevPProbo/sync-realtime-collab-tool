package com.devajit.sync_realtimecontentwriting.domain.use_case

import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.gptbot.models.ChoiceBody
import com.devajit.gptbot.models.GptBody
import com.devajit.sync_realtimecontentwriting.domain.repository.IChatGptRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GptUseCase @Inject constructor(
    private val gptRepo : IChatGptRepo
) {

    fun getGptSuggestion(gptBody: GptBody) : Flow<Resources<List<ChoiceBody>>> {
        return gptRepo.getGptSuggestion(gptBody)
    }
}