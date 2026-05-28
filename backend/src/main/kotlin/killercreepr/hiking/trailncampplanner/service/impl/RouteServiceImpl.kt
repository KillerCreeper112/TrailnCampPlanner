package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.service.RouteService

class RouteServiceImpl(
  val routeRepository: RouteRepository,
  val tripRepository: TripRepository,
): RouteService {
  override fun createRoute(
    tripId: Long,
    dto: CreateRouteRequest
  ): RouteDto {
    val trip = tripRepository.findById(tripId)
      .orElseThrow { ResourceNotFoundException("Trip with ID $tripId not found") }
    val route = Route(trip = trip)
    return routeRepository.save(route).mapToDto()
  }

  override fun deleteRoute(id: Long) {
    if(!routeRepository.existsById(id)) throw ResourceNotFoundException("Route with ID $id not found")
    routeRepository.deleteById(id)
  }
}