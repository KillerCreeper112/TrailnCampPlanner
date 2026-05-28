package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.TripService
import java.time.Instant

class TripServiceImpl(
  val userRepository: UserRepository,
  val tripRepository: TripRepository
): TripService {
  override fun createTrip(
    userId: Long,
    dto: CreateTripRequest
  ): TripDto {
    val user = userRepository.findById(userId).orElseThrow{
      ResourceNotFoundException("User with ID $userId not found")
    }
    val trip = Trip(
      createdAt = Instant.now(),
      name = dto.name,
      description = dto.description,
      user = user
    )
    return tripRepository.save(trip).mapToDto()
  }

  override fun getUserTrips(userId: Long): List<TripDto> {
    val user = userRepository.findById(userId).orElseThrow {
      ResourceNotFoundException("User with ID $userId not found")
    }
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
}