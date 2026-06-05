package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.service.RouteService
import org.springframework.stereotype.Service

@Service
class RouteServiceImpl(
  val routeRepository: RouteRepository,
  val tripRepository: TripRepository,
): RouteService {
  override fun addRoutePoint(id: Long, userId: Long, dto: CreateRoutePointRequest): RoutePointDto {
    val route = routeRepository.findByIdAndTripUserId(id, userId) ?:
    throw ResourceNotFoundException("Route with id $id not found")
    val point = RoutePoint(
      latitude = dto.latitude,
      longitude = dto.longitude,
      orderIndex = dto.orderIndex,
    ).also {
      it.route = route
    }
    route.points.add(point)
    routeRepository.save(route)
    return route.points.last().mapToDto() //point.mapToDto()
  }

  override fun createRoute(
    tripId: Long,
    userId: Long,
    dto: CreateRouteRequest
  ): RouteDto {
    val trip = tripRepository.findByIdAndUserId(tripId, userId) ?:
    throw ResourceNotFoundException("Trip with ID $tripId not found")
    val route = Route(
      name = dto.name
    ).also { it.trip = trip }
    return routeRepository.save(route).mapToDto()
  }

  override fun deleteRoute(id: Long, userId: Long) {
    if(!routeRepository.existsById(id)) throw ResourceNotFoundException("Route with ID $id not found")
    routeRepository.deleteById(id)
  }
}