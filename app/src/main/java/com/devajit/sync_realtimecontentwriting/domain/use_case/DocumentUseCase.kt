package com.devajit.sync_realtimecontentwriting.domain.use_case

import android.util.Log
import androidx.compose.runtime.MutableState
import com.devajit.dictionaryapp.core.utils.Resources
import com.devajit.sync_realtimecontentwriting.domain.model.DocumentModel
import com.devajit.sync_realtimecontentwriting.domain.model.PromptModel
import com.devajit.sync_realtimecontentwriting.domain.repository.IDocumentRepository
import kotlinx.coroutines.flow.Flow

class DocumentUseCase(
    private val documentRepository: IDocumentRepository
) {

    val _documentDetails = documentRepository.documentDetails
    var recentDocuments: MutableState<ArrayList<DocumentModel>> = documentRepository.recentDocuments
    fun updateContent(documentId : String, content : String) : Flow<Boolean> {
        return documentRepository.updateContent(documentId, content)
    }

    fun updateDocumentTitle(documentId : String, title : String) : Flow<Boolean> {
        return documentRepository.updateTitle(documentId, title)
    }

    fun updateCursorPosition(documentId : String, position: Int, userId: String) : Flow<Boolean> {
        return documentRepository.updateCursorPosition(documentId, position, userId)
    }

    fun getDocumentDetails(documentId : String, userId: String) : Flow<Resources<DocumentModel>> {
        return documentRepository.getDocumentDetails(documentId, userId)
    }

    fun switchUserToOffline(documentId : String, userId : String) : Flow<Boolean> {
        Log.d("switchOffUseCase", userId.toString())
        return documentRepository.switchUserToOffline(documentId = documentId,
            userId = userId)
    }

    fun switchUserToOnline(documentId : String, userId : String) : Flow<Boolean> {
        Log.d("switchOnUseCase", userId.toString())
        return documentRepository.switchUserToOnline(documentId = documentId,
            userId = userId)
    }

    fun addMessageToPrompt(documentId : String, message : PromptModel) : Flow<Boolean> {
        return documentRepository.addPromptMessage(documentId = documentId, message)
    }

    fun clearPromptList(documentId : String) : Flow<Boolean> {
        return documentRepository.clearPromptsList(documentId = documentId)
    }

    fun createDocument(userId: String): Flow<DocumentModel> {
        return documentRepository.createDocument(userId)

    }

    fun deleteDocument(documentId: String) {
        return documentRepository.deleteDocument(documentId)

    }

    fun getUserDocumentsByUserId(userId: String): Flow<List<DocumentModel>> {
        return documentRepository.getUserDocumentsByUserId(userId)

    }

    suspend fun getRecentDocumentsByUserId(userId: String) {
        documentRepository.getRecentDocumentsByUserId(userId)

    }

    fun getDocumentById(documentId: String) : Flow<DocumentModel> {
        return documentRepository.getDocumentById(documentId)
    }

    fun addToRecentDocuments(userId: String,documentId : String) {
        Log.d("UseCaseAddToRecent","yes")
        documentRepository.addToRecentDocuments(userId, documentId)
    }

}