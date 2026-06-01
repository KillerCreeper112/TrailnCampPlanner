package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest

interface RouteService {
  fun addRoutePoint(id: Long, userId: Long, dto: CreateRoutePointRequest): RoutePointDto
  fun createRoute(tripId: Long, userId: Long, dto: CreateRouteRequest): RouteDto
  fun deleteRoute(id: Long, userId: Long)
}