package com.zorbeytorunoglu.turla.repository

import com.zorbeytorunoglu.turla.model.Gender
import com.zorbeytorunoglu.turla.model.User
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository: MongoRepository<User, String> {

    fun findUserById(id: String): User?

    fun findUserByEmail(email: String): User?

    fun findUserByPhoneNumber(phoneNumber: String): User?

    fun findUsersByGender(gender: Gender): List<User>

    fun findUserByNameAndLastName(name: String, lastName: String): User?

}