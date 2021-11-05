package com.nrk.sephora.assignmentshepora.source.remote

data class ApiReponse<out R>(
    val data: R?,
    val error: ErrorRecord?
)

sealed class ErrorRecord {
    object ServerError: ErrorRecord() {
        override fun toString(): String = "Error from server"
    }
    object NetworkError: ErrorRecord() {
        override fun toString(): String = "Network Error"
    }
    object GenericError: ErrorRecord() {
        override fun toString(): String = "There was some error"
    }
}