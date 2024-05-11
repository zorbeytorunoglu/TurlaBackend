package com.zorbeytorunoglu.turla.controller

import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result
import org.springframework.http.ResponseEntity

interface ControllerResultHandler {
    fun <T, E: Error> handleResult(result: Result<T, E>): ResponseEntity<Result<T, E>>
}

class ControllerResultHandlerImpl : ControllerResultHandler {
    override fun <T, E: Error> handleResult(result: Result<T, E>): ResponseEntity<Result<T, E>> {
        return when (result) {
            is Result.Error -> {
                ResponseEntity.status(result.httpStatusCode).body(result)
            }
            is Result.Success -> {
                ResponseEntity.status(result.httpStatusCode).body(result)
            }
        }
    }
}