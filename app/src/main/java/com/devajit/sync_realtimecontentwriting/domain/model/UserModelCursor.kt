package com.devajit.sync_realtimecontentwriting.domain.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class UserModelCursor(
    val userDetails : UserModel?,
    val color : Color,
    val position : Int?
)