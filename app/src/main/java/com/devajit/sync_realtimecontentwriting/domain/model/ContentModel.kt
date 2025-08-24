package com.devajit.sync_realtimecontentwriting.domain.model

data class ContentModel(
    var content : String?,
    val lastEditedBy : String? = ""
)
