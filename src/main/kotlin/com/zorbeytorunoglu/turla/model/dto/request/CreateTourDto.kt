package com.zorbeytorunoglu.turla.model.dto.request

import com.zorbeytorunoglu.turla.model.City
import com.zorbeytorunoglu.turla.model.PreferredGender
import java.time.LocalDate

data class CreateTourDto(
    val hostId: String,
    val name: String,
    val description: String,
    val travellerLimit: Int,
    val preferredGender: PreferredGender,
    val expense: Double,
    val startCity: City,
    val startDate: LocalDate,
    val endDate: LocalDate
)
