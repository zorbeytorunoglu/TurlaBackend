package com.zorbeytorunoglu.turla.controller

import com.zorbeytorunoglu.turla.model.User
import com.zorbeytorunoglu.turla.model.dto.request.CreateUserDto
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result
import com.zorbeytorunoglu.turla.service.impl.UserServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val service: UserServiceImpl
): ControllerResultHandler by ControllerResultHandlerImpl() {

    @GetMapping
    fun getUsers(): ResponseEntity<Result<List<User>, Error>> =
        handleResult(service.getUsers())

    @PostMapping
    fun createUser(
        @Valid @RequestBody createUserDto: CreateUserDto
    ): ResponseEntity<Result<User, Error>> =
        handleResult(service.createUser(createUserDto))

}