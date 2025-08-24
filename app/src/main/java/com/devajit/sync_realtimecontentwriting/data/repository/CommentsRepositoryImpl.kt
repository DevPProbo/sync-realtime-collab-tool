package com.devajit.sync_realtimecontentwriting.data.repository

import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.sync_realtimecontentwriting.data.remote.FirebaseManager
import com.devajit.sync_realtimecontentwriting.domain.model.CommentsModel
import com.devajit.sync_realtimecontentwriting.domain.repository.ICommentsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentsRepositoryImpl @Inject constructor(
    private val firebaseManager: FirebaseManager
) : ICommentsRepo {
    override fun getDocumentComments() {
        firebaseManager.getDocumentComments()
    }

    override fun addComment(
        documentId: String,
        comment: CommentsModel
    ): Flow<Resources<CommentsModel>> = flow {
        try {
            val addedComment = firebaseManager.addCommentToDocument(documentId, comment)
            emit(
                Resources.Success(addedComment)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            emit(
                Resources.Error(comment, message = e.message.toString())
            )
        }
    }

    override fun deleteComment(documentId: String, commentId: String): Flow<Resources<Boolean>> {
        return try {
            firebaseManager.deleteComment(documentId, commentId)
            flow {
                emit(
                    Resources.Success(true)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            flow {
                emit(
                    Resources.Success(false)
                )
            }
        }
    }
}