package com.zorbeytorunoglu.turla.service.impl

import com.zorbeytorunoglu.turla.model.City
import com.zorbeytorunoglu.turla.model.Tour
import com.zorbeytorunoglu.turla.model.User
import com.zorbeytorunoglu.turla.model.dto.request.AddTravellerDto
import com.zorbeytorunoglu.turla.model.dto.request.CreateTourDto
import com.zorbeytorunoglu.turla.model.dto.request.RemoveTravellerDto
import com.zorbeytorunoglu.turla.repository.TourRepository
import com.zorbeytorunoglu.turla.repository.UserRepository
import com.zorbeytorunoglu.turla.service.TourService
import com.zorbeytorunoglu.turla.service.TourServiceError
import com.zorbeytorunoglu.turla.service.UserServiceError
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class TourServiceImpl(
    private val tourRepository: TourRepository,
    private val userRepository: UserRepository
): TourService {

    override fun getTour(tourId: String): Result<Tour, Error> {
        tourRepository.findTourById(tourId)?.let {
            return Result.Success(it)
        } ?: run {
            return Result.Error(TourServiceError.TOUR_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getTours(): Result<List<Tour>, Error> =
        Result.Success(tourRepository.findAll().filterNotNull().toList())

    override fun getToursByHostId(userId: String): Result<List<Tour>, Error> {
        userRepository.findUserById(userId)?.let { host ->
            return Result.Success(tourRepository.findToursByHostId(host.id!!))
        } ?: run {
            return Result.Error(TourServiceError.HOST_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getToursByCityName(cityString: String): Result<List<Tour>, Error> {
        runCatching { City.valueOf(cityString.uppercase(Locale.US)) }.getOrNull()?.let { city ->
            return Result.Success(tourRepository.findToursByStartCity(city))
        } ?: run {
            return Result.Error(TourServiceError.CITY_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getActiveHostedTours(userId: String): Result<List<Tour>, Error> {
        userRepository.findUserById(userId)?.let { host ->
            return Result.Success(tourRepository.findToursByHostAndComplete(host, false))
        } ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getCompletedHostedTours(userId: String): Result<List<Tour>, Error> {
        userRepository.findUserById(userId)?.let { host ->
            return Result.Success(tourRepository.findToursByHostAndComplete(host, true))
        } ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getActiveJoinedTours(userId: String): Result<List<Tour>, Error> {
        userRepository.findUserById(userId)?.let { traveller ->
            return Result.Success(
                tourRepository.findToursByTravellersContainsAndComplete(traveller, complete = false)
            )
        } ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun getCompletedJoinedTours(userId: String): Result<List<Tour>, Error> {
        userRepository.findUserById(userId)?.let { traveller ->
            return Result.Success(
                tourRepository.findToursByTravellersContainsAndComplete(traveller, true)
            )
        } ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun createTour(createTourDto: CreateTourDto): Result<Tour, Error> {

        val host = userRepository.findUserById(createTourDto.hostId) ?: run {
            return Result.Error(TourServiceError.HOST_NOT_FOUND, HttpStatus.NOT_FOUND)
        }

        return Result.Success(
            tourRepository.insert(
                Tour(
                    name = createTourDto.name,
                    description = createTourDto.description,
                    host = host,
                    travellers = emptyList(),
                    travellerLimit = createTourDto.travellerLimit,
                    imageURLs = emptyList(),
                    preferredGender = createTourDto.preferredGender,
                    expense = createTourDto.expense,
                    startDate = createTourDto.startDate,
                    startCity = createTourDto.startCity,
                    complete = false,
                    endDate = createTourDto.endDate
                )
            )
        )

    }

    override fun deleteTour(tourId: String): Result<Unit, Error> {
        tourRepository.findTourById(tourId)?.let { tour ->
            return Result.Success(tourRepository.delete(tour))
        } ?: run {
            return Result.Error(TourServiceError.TOUR_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
    }

    override fun addTraveller(addTravellerDto: AddTravellerDto): Result<Unit, Error> {
        val tour = tourRepository.findTourById(addTravellerDto.tourId) ?: run {
            return Result.Error(TourServiceError.TOUR_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
        val user = userRepository.findUserById(addTravellerDto.userId) ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }
        if (tour.travellers.any { it.id == user.id }) {
            return Result.Error(TourServiceError.USER_ALREADY_JOINED, HttpStatus.CONFLICT)
        }

        tourRepository.save(
            tour.copy(
                travellers = mutableListOf<User>().apply {
                    addAll(tour.travellers)
                    add(user)
                }.toList()
            )
        )

        return Result.Success(Unit)
    }

    override fun removeTraveller(removeTravellerDto: RemoveTravellerDto): Result<Unit, Error> {
        val tour = tourRepository.findTourById(removeTravellerDto.tourId) ?: run {
            return Result.Error(TourServiceError.TOUR_NOT_FOUND, HttpStatus.NOT_FOUND)
        }

        val user = userRepository.findUserById(removeTravellerDto.userId) ?: run {
            return Result.Error(UserServiceError.USER_NOT_FOUND, HttpStatus.NOT_FOUND)
        }

        if (!tour.travellers.any { it.id == user.id }) {
            return Result.Error(TourServiceError.USER_NOT_A_TRAVELLER, HttpStatus.NOT_FOUND)
        }

        tourRepository.save(
            tour.copy(
                travellers = mutableListOf<User>().apply {
                    addAll(tour.travellers)
                    remove(user)
                }.toList()
            )
        )

        return Result.Success(Unit)
    }

}