package com.zorbeytorunoglu.turla.service.common.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

typealias RootError = Error

sealed class Result<out D, out E: RootError> {
    data class Success<out D, out E: RootError>(
        val data: D, val httpStatusCode: HttpStatusCode = HttpStatus.OK
    ) : Result<D, E>()
    data class Error<out D, out E: RootError>(
        val error: E, val httpStatusCode: HttpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR
    ): Result<D, E>()
}