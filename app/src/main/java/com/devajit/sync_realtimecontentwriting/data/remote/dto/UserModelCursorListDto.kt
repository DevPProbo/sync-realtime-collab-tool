package com.devajit.sync_realtimecontentwriting.data.remote.dto

import androidx.compose.runtime.Stable

@Stable
data class UserModelCursorListDto(
    val usersList : ArrayList<UserModelCursorDto>,
)