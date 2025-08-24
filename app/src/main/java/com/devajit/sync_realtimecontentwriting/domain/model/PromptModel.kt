package com.devajit.sync_realtimecontentwriting.domain.model

import com.devajit.gptbot.models.MessageBody
import javax.annotation.concurrent.Immutable

@Immutable
data class PromptModel(
    var message: String = "",
    val role: String = "",
    val sentBy: UserModel?,
    val timeStamp: Long
) {
    fun toMessageModel(): MessageBody {
        return MessageBody(role, message)
    }
}