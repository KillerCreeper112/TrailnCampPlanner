package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.RoutePointRepository
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.service.RoutePointService

class RoutePointServiceImpl(
  val routePointRepository: RoutePointRepository,
  val routeRepository: RouteRepository
): RoutePointService {
  override fun createRoutePoint(
    routeId: Long,
    dto: CreateRoutePointRequest
  ): RoutePointDto {
    val route = routeRepository.findById(routeId).orElseThrow {
      ResourceNotFoundException("Route with ID $routeId not found")
    }
    val point = RoutePoint(
      latitude = dto.latitude,
      longitude = dto.longitude,
      orderIndex = dto.orderIndex,
      route = route
    )
    return routePointRepository.save(point).mapToDto()
  }
}