package com.zorbeytorunoglu.turla.controller

import com.zorbeytorunoglu.turla.model.Tour
import com.zorbeytorunoglu.turla.model.dto.request.AddTravellerDto
import com.zorbeytorunoglu.turla.model.dto.request.CreateTourDto
import com.zorbeytorunoglu.turla.model.dto.request.RemoveTravellerDto
import com.zorbeytorunoglu.turla.service.common.error.Error
import com.zorbeytorunoglu.turla.service.common.error.Result
import com.zorbeytorunoglu.turla.service.impl.TourServiceImpl
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tours")
class TourController(
    private val service: TourServiceImpl
): ControllerResultHandler by ControllerResultHandlerImpl() {

    @GetMapping
    fun getTours(): ResponseEntity<Result<List<Tour>, Error>> =
        handleResult(service.getTours())

    @GetMapping("/{city}")
    fun getTours(@PathVariable city: String): ResponseEntity<Result<List<Tour>, Error>> =
        handleResult(service.getToursByCityName(city))

    @PostMapping
    fun createTour(@Valid @RequestBody createTourDto: CreateTourDto): ResponseEntity<Result<Tour?, Error>> =
        handleResult(service.createTour(createTourDto))

    @DeleteMapping("/{tourId}")
    fun deleteTour(@PathVariable tourId: String): ResponseEntity<Result<Unit, Error>> =
        handleResult(service.deleteTour(tourId))

    @PatchMapping("/add-traveller")
    fun addTraveller(
        @Valid @RequestBody addTravellerDto: AddTravellerDto
    ): ResponseEntity<Result<Unit, Error>> =
        handleResult(service.addTraveller(addTravellerDto))

    @PatchMapping("/remove-traveller")
    fun removeTraveller(
        @Valid @RequestBody removeTravellerDto: RemoveTravellerDto
    ): ResponseEntity<Result<Unit, Error>> =
        handleResult(service.removeTraveller(removeTravellerDto))

}