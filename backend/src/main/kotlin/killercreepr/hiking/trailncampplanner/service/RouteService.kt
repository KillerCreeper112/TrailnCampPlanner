package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto

interface RouteService {
  fun createRoute(tripId: Long, dto: CreateRouteRequest): RouteDto
  fun deleteRoute(id: Long)
}