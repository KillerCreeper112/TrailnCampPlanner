package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto

interface RoutePointService {
  fun createRoutePoint(routeId: Long, dto: CreateRoutePointRequest): RoutePointDto
}