package com.zorbeytorunoglu.turla.service

import com.zorbeytorunoglu.turla.model.User
import com.zorbeytorunoglu.turla.model.dto.request.CreateUserDto
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result

interface UserService {

    fun getUsers(): Result<List<User>, Error>

    fun getUser(userId: String): Result<User, Error>

    fun createUser(createUserDto: CreateUserDto): Result<User, Error>

}

enum class UserServiceError: Error {
    USER_NOT_FOUND,
    USER_WITH_THAT_PHONE_ALREADY_EXIST,
    USER_WITH_THAT_EMAIL_ALREADY_EXIST
}