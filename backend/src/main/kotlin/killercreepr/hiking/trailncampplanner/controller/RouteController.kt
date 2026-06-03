package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.dto.CreateRoutePointRequest
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.UpdateRoutePointRequest
import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import killercreepr.hiking.trailncampplanner.service.RouteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/routes")
class RouteController(
  val routeService: RouteService
) {
  @PostMapping("/{id}")
  fun addRoutePoint(@AuthenticationPrincipal user: Any?,
                    @PathVariable id: Long,
                    @RequestBody dto: CreateRoutePointRequest): ResponseEntity<RoutePointDto> {
    return ResponseEntity(routeService.addRoutePoint(
      id, (user as PrincipalUser).id, dto
    ), HttpStatus.CREATED)
  }

  @DeleteMapping("/{id}")
  fun deleteRoute(@AuthenticationPrincipal user: PrincipalUser,
                       @PathVariable id: Long): ResponseEntity<String> {
    routeService.deleteRoute(id, user.id)
    return ResponseEntity.ok("Deleted route $id")
  }
}