package org.mifos.openbanking.domain.usecase.base

import org.mifos.openbanking.base.Response

abstract class BaseUseCase<R : BaseRequest, T> {

    protected lateinit var request: R

    suspend fun execute(request: R): Response<T> {
        this.request = request

        val validated = request.validate()
        if (validated) return run()
        return Response.Error(IllegalArgumentException())
    }

    abstract suspend fun run(): Response<T>
}
