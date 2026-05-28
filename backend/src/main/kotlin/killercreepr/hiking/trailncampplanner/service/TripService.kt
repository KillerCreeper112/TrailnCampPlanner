package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.TripDto

interface TripService {
  fun createTrip(dto: CreateTripRequest): TripDto
  fun getUserTrips(): List<TripDto>
  fun getTrip(id: Long): TripDto
  fun getAllTrips(): List<TripDto>
  fun deleteTrip(id: Long)
  fun deleteTripIfOwner(id: Long, userId: Long)
}