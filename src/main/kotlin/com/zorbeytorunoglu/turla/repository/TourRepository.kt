package com.zorbeytorunoglu.turla.repository

import com.zorbeytorunoglu.turla.model.City
import com.zorbeytorunoglu.turla.model.PreferredGender
import com.zorbeytorunoglu.turla.model.Tour
import com.zorbeytorunoglu.turla.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDate

interface TourRepository: MongoRepository<Tour, String> {

    fun findToursByStartCity(city: City): List<Tour>

    fun findTourById(id: String): Tour?

    fun findToursByComplete(complete: Boolean): List<Tour>

    fun findTourByHost(host: User): Tour

    fun findTourByHostId(hostId: String): Tour

    fun findToursByHost(host: User): List<Tour>

    fun findToursByHostId(hostId: String): List<Tour>

    fun findToursByHostAndComplete(host: User, complete: Boolean): List<Tour>

    fun findToursByTravellersContains(user: User): List<Tour>

    fun findToursByTravellersContainsAndComplete(user: User, complete: Boolean): List<Tour>

    fun findToursByPreferredGender(preferredGender: PreferredGender): List<Tour>

    fun findTourByStartDate(startDate: LocalDate): Tour

    fun findTourByStartCityAndStartDate(city: City, startDate: LocalDate): Tour

}