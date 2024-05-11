package com.zorbeytorunoglu.turla.model.dto.request

import com.zorbeytorunoglu.turla.model.Gender
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class CreateUserDto(
    @field:NotBlank(message = "Name is required.")
    @field:Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    val name: String,
    @field:NotBlank(message = "Last name is required.")
    @field:Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    val lastName: String,
    @field:NotBlank(message = "Phone number is required")
    val phoneNumber: String,
    @field:Email(message = "Invalid email format")
    val email: String? = null,
    @field:NotNull(message = "Gender is required")
    val gender: Gender,
    val profilePhotoUrl: String? = null,
    @field:NotNull(message = "Birth date is required")
    val birthDate: LocalDate
)