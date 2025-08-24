package com.devajit.sync_realtimecontentwriting.presentation

sealed class UIEvents {
    data class ShowSnackbar(val message: String) : UIEvents()
    data class ShowCodeLoading(val message: String = "0") : UIEvents()

    data class ShowCreateLoading(val message: String = "0") : UIEvents()

    data class DocumentCodeApplied(val documentId:String):UIEvents()

    data class ShareDocument(val documentId:String): UIEvents()
    data class DefaultState(val message: String = "0"): UIEvents()
}