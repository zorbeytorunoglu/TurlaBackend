package com.zorbeytorunoglu.turla.service

import com.zorbeytorunoglu.turla.model.City
import com.zorbeytorunoglu.turla.model.Tour
import com.zorbeytorunoglu.turla.model.dto.request.AddTravellerDto
import com.zorbeytorunoglu.turla.model.dto.request.CreateTourDto
import com.zorbeytorunoglu.turla.model.dto.request.RemoveTravellerDto
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result

interface TourService {

    fun getTour(tourId: String): Result<Tour, Error>

    fun getTours(): Result<List<Tour>, Error>

    fun getToursByCityName(cityString: String): Result<List<Tour>, Error>

    fun getToursByHostId(userId: String): Result<List<Tour>, Error>

    fun getActiveHostedTours(userId: String): Result<List<Tour>, Error>

    fun getCompletedHostedTours(userId: String): Result<List<Tour>, Error>

    fun getActiveJoinedTours(userId: String): Result<List<Tour>, Error>

    fun getCompletedJoinedTours(userId: String): Result<List<Tour>, Error>

    fun createTour(createTourDto: CreateTourDto): Result<Tour, Error>

    fun deleteTour(tourId: String): Result<Unit, Error>

    fun addTraveller(addTravellerDto: AddTravellerDto): Result<Unit, Error>

    fun removeTraveller(removeTravellerDto: RemoveTravellerDto): Result<Unit, Error>

}

enum class TourServiceError: Error {
    REACHED_LIMIT,
    ALREADY_EXIST,
    USER_RESTRICTED,
    HOST_NOT_FOUND,
    TOUR_NOT_FOUND,
    USER_ALREADY_JOINED,
    USER_NOT_A_TRAVELLER,
    CITY_NOT_FOUND,
    INVALID_TOUR_ID,
}