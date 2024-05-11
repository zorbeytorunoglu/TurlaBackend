package com.zorbeytorunoglu.turla.service.impl

import com.zorbeytorunoglu.turla.model.User
import com.zorbeytorunoglu.turla.model.dto.request.CreateUserDto
import com.zorbeytorunoglu.turla.repository.UserRepository
import com.zorbeytorunoglu.turla.service.UserService
import com.zorbeytorunoglu.turla.service.UserServiceError
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun getUsers(): Result<List<User>, Error> =
        Result.Success(userRepository.findAll())

    override fun getUser(userId: String): Result<User, Error> {
        userRepository.findUserById(userId)?.let {
            return Result.Success(it)
        } ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND)
        }
    }

    override fun createUser(createUserDto: CreateUserDto): Result<User, Error> {

        userRepository.findAll().firstOrNull {
            it.phoneNumber == createUserDto.phoneNumber || it.email == createUserDto.email
        }?.let { existingUser ->
            val error = when (existingUser.phoneNumber) {
                createUserDto.phoneNumber -> UserServiceError.USER_WITH_THAT_PHONE_ALREADY_EXIST
                else -> UserServiceError.USER_WITH_THAT_EMAIL_ALREADY_EXIST
            }
            return Result.Error(error, httpStatusCode = HttpStatus.CONFLICT)
        }

        return Result.Success(
            userRepository.insert(
                User(
                    name = createUserDto.name,
                    lastName = createUserDto.lastName,
                    phoneNumber = createUserDto.phoneNumber,
                    email = createUserDto.email,
                    birthDate = createUserDto.birthDate,
                    gender = createUserDto.gender
                )
            )
        )

    }

}