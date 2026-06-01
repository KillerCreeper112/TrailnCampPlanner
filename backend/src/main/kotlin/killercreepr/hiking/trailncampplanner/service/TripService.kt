package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.dto.UpdateTripRequest

interface TripService {
  fun createTrip(userId: Long, dto: CreateTripRequest): TripDto
  fun updateTrip(id: Long, userId: Long, dto: UpdateTripRequest): TripDto
  fun getUserTrips(userId: Long): List<TripDto>
  fun deleteTrip(id: Long, userId: Long)
  fun getTrip(id: Long, userId: Long): TripDto

  fun addRouteToTrip(id: Long, userId: Long, dto: CreateRouteRequest): RouteDto
  fun removeRouteFromTrip(id: Long, userId: Long, routeId: Long)
  fun getRoutesFromTrip(id: Long, userId: Long): List<RouteDto>
}