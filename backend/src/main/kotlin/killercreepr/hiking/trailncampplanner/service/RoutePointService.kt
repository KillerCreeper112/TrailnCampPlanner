package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest

interface RoutePointService {
  fun createRoutePoint(routeId: Long, dto: CreateRoutePointRequest): RoutePointDto

  fun updateRoutePoint(id: Long, userId: Long, dto: UpdateRoutePointRequest): RoutePointDto
}