package com.devajit.sync_realtimecontentwriting.data.remote.dto

import com.devajit.sync_realtimecontentwriting.domain.model.ContentModel

data class ContentModelDto(
    val content : String? = "",
    val lastEditedBy : String? = ""
) {

    fun toContentModel() : ContentModel {
        return ContentModel(content, lastEditedBy)
    }
}
