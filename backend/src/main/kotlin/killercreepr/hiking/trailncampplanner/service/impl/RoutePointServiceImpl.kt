package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.RoutePointRepository
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.service.RoutePointService
import org.springframework.stereotype.Service

@Service
class RoutePointServiceImpl(
  val routePointRepository: RoutePointRepository,
  val routeRepository: RouteRepository
): RoutePointService {
  override fun createRoutePoint(
    routeId: Long,
    dto: CreateRoutePointRequest
  ): RoutePointDto {
    val route = routeRepository.findById(routeId).orElseThrow {
      ResourceNotFoundException("Route with id $routeId not found")
    }
    val point = RoutePoint(
      latitude = dto.latitude,
      longitude = dto.longitude,
      orderIndex = dto.orderIndex
    ).also{it.route = route}
    return routePointRepository.save(point).mapToDto()
  }

  override fun deleteRoutePoint(id: Long, userId: Long) {
    if(!routePointRepository.existsById(id)) throw ResourceNotFoundException("Route point with ID $id not found")
    routePointRepository.deleteById(id)
  }

  override fun updateRoutePoint(
    id: Long,
    userId: Long,
    dto: UpdateRoutePointRequest
  ): RoutePointDto {
    val point = routePointRepository.findByIdAndRouteTripUserId(id, userId) ?:
    throw ResourceNotFoundException("Route point with id $id not found")
    point.apply {
      this.latitude = dto.latitude
      this.longitude = dto.longitude
    }
    return routePointRepository.save(point).mapToDto()
  }
}