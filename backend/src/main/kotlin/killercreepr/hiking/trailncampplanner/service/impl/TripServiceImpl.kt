package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.*
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.entity.User
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.TripService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TripServiceImpl(
  val userRepository: UserRepository,
  val tripRepository: TripRepository
): TripService {
  override fun createTrip(
    userId: Long,
    dto: CreateTripRequest
  ): TripDto {
    val user = getUser(userId)
    val trip = Trip(
      name = dto.name,
      description = dto.description
    ).also { it.createdAt = Instant.now(); it.user = user }
    return tripRepository.save(trip).mapToDto()
  }

  private fun getUser(id: Long): User{
    return userRepository.findById(id).orElseThrow {
      ResourceNotFoundException("User with id ${id} not found")
    }
  }

  override fun updateTrip(id: Long, userId: Long, dto: UpdateTripRequest): TripDto {
    val trip = tripRepository.findByIdAndUserId(id, userId) ?:
    throw ResourceNotFoundException("Trip with id $id not found")

    trip.apply {
      name = dto.name
      description = dto.description
      startDate = dto.startDate
      endDate = dto.endDate
      difficulty = dto.difficulty
    }
    return tripRepository.save(trip).mapToDto()
  }

  override fun getUserTrips(userId: Long): List<TripDto> {
    val user = getUser(userId)
    return user.trips.map { trip -> trip.mapToDto() }
  }

  override fun deleteTrip(id: Long, userId: Long) {
    checkOwner(id, userId)
    tripRepository.deleteById(id)
  }

  private fun checkOwner(id: Long, userId: Long) {
    if(!tripRepository.existsByIdAndUserId(id, userId))
      throw AccessDeniedException("Access denied")
  }

  override fun getTrip(
    id: Long,
    userId: Long
  ): TripDto {
    checkOwner(id, userId)
    return tripRepository.findById(id)
      .orElseThrow { ResourceNotFoundException("Trip with ID $id not found") }.mapToDto()
  }

  override fun addRouteToTrip(
    id: Long,
    userId: Long,
    dto: CreateRouteRequest
  ): RouteDto {
    checkOwner(id, userId)
    val trip = tripRepository.findById(id).orElseThrow {
      ResourceNotFoundException("Trip with id $id not found")
    }
    val route = Route(
      name = dto.name
    ).apply {
      this.trip = trip
    }
    trip.routes.add(route)
    val saved = tripRepository.save(trip)
    return saved.routes.last().mapToDto() //route.mapToDto()
  }

  override fun removeRouteFromTrip(id: Long, userId: Long, routeId: Long) {
    TODO("Not yet implemented")
  }

  override fun getRoutesFromTrip(
    id: Long,
    userId: Long
  ): List<RouteDto> {
    TODO("Not yet implemented")
  }
}