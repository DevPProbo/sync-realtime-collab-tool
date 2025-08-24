package com.devajit.sync_realtimecontentwriting.domain.repository

import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.sync_realtimecontentwriting.domain.model.CommentsModel
import kotlinx.coroutines.flow.Flow

interface ICommentsRepo {
    fun getDocumentComments()
    fun addComment(documentId : String, comment : CommentsModel) :  Flow<Resources<CommentsModel>>
    fun deleteComment(documentId : String, commentId : String) : Flow<Resources<Boolean>>


}