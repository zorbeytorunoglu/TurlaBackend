package com.zorbeytorunoglu.turla.model

data class Review(
    val id: Long,
    val from: User,
    val to: User,
    val stars: Double,
    val comment: String? = null
)