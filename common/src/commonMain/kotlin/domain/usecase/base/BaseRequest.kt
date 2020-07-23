package org.mifos.openbanking.common.domain.usecase.base

interface BaseRequest {
    fun validate(): Boolean
}
