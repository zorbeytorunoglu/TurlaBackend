package com.zorbeytorunoglu.turla.model.dto.request

import jakarta.validation.constraints.NotNull

data class AddTravellerDto(
    @field:NotNull(message = "Tour ID is required")
    val tourId: String,

    @field:NotNull(message = "User ID is required")
    val userId: String
)
