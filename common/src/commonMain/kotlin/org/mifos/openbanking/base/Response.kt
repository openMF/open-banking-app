package org.mifos.openbanking.base

sealed class Response<out T> {
    class Success<out T>(val data: T) : Response<T>()

    data class Error(
        val exception: Throwable,
        val code: Int? = null,
        val message: String? = null,
        val method: String? = null,
        val path: String? = null
    ) : Response<Nothing>()
}
