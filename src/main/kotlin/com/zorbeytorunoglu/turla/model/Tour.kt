package com.zorbeytorunoglu.turla.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "tours")
data class Tour(
    @Id
    val id: String? = null,
    val name: String,
    val description: String,
    @DBRef
    val host: User,
    @DBRef
    val travellers: List<User>,
    val imageURLs: List<String>,
    val travellerLimit: Int,
    val preferredGender: PreferredGender,
    val expense: Double,
    val startCity: City,
    val startDate: LocalDate,
    val endDate: LocalDate?,
    val complete: Boolean
)