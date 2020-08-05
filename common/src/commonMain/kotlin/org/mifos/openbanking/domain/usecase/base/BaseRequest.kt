package org.mifos.openbanking.domain.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}
