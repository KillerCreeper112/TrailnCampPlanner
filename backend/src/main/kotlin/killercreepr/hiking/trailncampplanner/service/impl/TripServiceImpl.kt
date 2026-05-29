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
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TripServiceImpl(
  val userRepository: UserRepository,
  val tripRepository: TripRepository
): TripService {
  override fun createTrip(
    dto: CreateTripRequest
  ): TripDto {
    val principal = extractPrincipalUser()
    val user = userRepository.findById(principal.id).orElseThrow {
      ResourceNotFoundException("User with id ${principal.id} not found")
    }
    val trip = Trip(
      name = dto.name,
      description = dto.description
    ).also { it.createdAt = Instant.now(); it.user = user }
    return tripRepository.save(trip).mapToDto()
  }

  override fun getUserTrips(): List<TripDto> {
    val user = extractPrincipalUser()
    return userRepository.findById(user.id).orElseThrow {
      ResourceNotFoundException("User with id ${user.id} not found")
    }.trips.map { trip -> trip.mapToDto() }
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