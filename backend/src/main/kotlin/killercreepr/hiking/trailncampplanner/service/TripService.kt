package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.TripDto

interface TripService {
  fun createTrip(userId: Long, dto: CreateTripRequest): TripDto
  fun getUserTrips(userId: Long): List<TripDto>
  fun getTrip(id: Long): TripDto
  fun getAllTrips(): List<TripDto>
  fun deleteTrip(id: Long)
}