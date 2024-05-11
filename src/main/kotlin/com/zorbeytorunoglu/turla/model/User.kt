package com.zorbeytorunoglu.turla.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "users")
data class User(
    @Id
    val id: String? = null,
    val name: String,
    val lastName: String,
    @Indexed(unique = true)
    val phoneNumber: String,
    @Indexed(unique = true)
    val email: String? = null,
    val profilePhotoUrl: String? = null,
    val birthDate: LocalDate,
    val gender: Gender
)