package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest
import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import killercreepr.hiking.trailncampplanner.service.RoutePointService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/route_points/")
class RoutePointController(
  val routePointService: RoutePointService
) {
  @PutMapping("/{routeId}")
  fun updateRoutePoint(@AuthenticationPrincipal user: PrincipalUser,
                       @PathVariable routeId: Long,
                       @RequestBody dto: UpdateRoutePointRequest): ResponseEntity<RoutePointDto> {
    return ResponseEntity.ok(routePointService.updateRoutePoint(routeId, user.id, dto))
  }

  @DeleteMapping("/{id}")
  fun deleteRoute(@AuthenticationPrincipal user: PrincipalUser,
                  @PathVariable id: Long): ResponseEntity<String> {
    routePointService.deleteRoutePoint(id, user.id)
    return ResponseEntity.ok("Deleted route point $id")
  }
}