package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.auth.extractPrincipalUser
import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.TripService
import org.springframework.security.access.AccessDeniedException
import java.time.Instant

class TripServiceImpl(
  val userRepository: UserRepository,
  val tripRepository: TripRepository
): TripService {
  override fun createTrip(
    dto: CreateTripRequest
  ): TripDto {
    val user = extractPrincipalUser()
    val trip = Trip(
      createdAt = Instant.now(),
      name = dto.name,
      description = dto.description,
      user = user
    )
    return tripRepository.save(trip).mapToDto()
  }

  override fun getUserTrips(): List<TripDto> {
    val user = extractPrincipalUser()
    return user.trips.map { trip -> trip.mapToDto() }
  }

  override fun getTrip(id: Long): TripDto = tripRepository.findById(id)
    .orElseThrow { ResourceNotFoundException("Trip with ID $id not found") }
    .mapToDto()

  override fun getAllTrips(): List<TripDto> = tripRepository.findAll().map { it.mapToDto() }

  override fun deleteTrip(id: Long) {
    getTrip(id)
    tripRepository.deleteById(id)
  }

  override fun deleteTripIfOwner(id: Long, userId: Long) {
    if(!tripRepository.existsByIdAndUserId(id, userId))
      throw AccessDeniedException("Access denied")
    tripRepository.deleteById(id)
  }
}