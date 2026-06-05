package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.auth.extractPrincipalUser
import killercreepr.hiking.trailncampplanner.dto.CreateNoteRequest
import killercreepr.hiking.trailncampplanner.dto.CreateRouteRequest
import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.NoteDto
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.dto.UpdateTripRequest
import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import killercreepr.hiking.trailncampplanner.service.NoteService
import killercreepr.hiking.trailncampplanner.service.TripService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trips")
class TripController(
  val tripService: TripService,
  val noteService: NoteService
) {

  @PostMapping
  fun createTrip(@AuthenticationPrincipal user: PrincipalUser, @RequestBody dto: CreateTripRequest):
    ResponseEntity<TripDto>{
    val created = tripService.createTrip(user.id, dto)
    return ResponseEntity(created, HttpStatus.CREATED)
  }

  @PutMapping("/{id}")
  fun updateTrip(@AuthenticationPrincipal user: PrincipalUser, @PathVariable id:Long, @RequestBody dto: UpdateTripRequest): ResponseEntity<TripDto>{
    return ResponseEntity.ok(tripService.updateTrip(id, user.id, dto))
  }

  @PostMapping("/{id}/routes")
  fun addRoute(@AuthenticationPrincipal user: PrincipalUser, @PathVariable id: Long, @RequestBody dto: CreateRouteRequest): ResponseEntity<RouteDto>{
    return ResponseEntity(
      tripService.addRouteToTrip(id, user.id, dto),
      HttpStatus.CREATED
    )
  }

  @GetMapping("{id}")
  fun getTrip(@AuthenticationPrincipal user: PrincipalUser, @PathVariable id: Long): ResponseEntity<TripDto>{
    return ResponseEntity.ok(tripService.getTrip(id, user.id))
  }

  @GetMapping
  fun getTrips(@AuthenticationPrincipal user: PrincipalUser): ResponseEntity<List<TripDto>>{
    return ResponseEntity.ok(tripService.getUserTrips(user.id))
  }

  @DeleteMapping("{id}")
  fun deleteTrip(
    @PathVariable("id") id: Long
  ): ResponseEntity<String>{
    val user = extractPrincipalUser()
    tripService.deleteTrip(id, user.id)
    return ResponseEntity.ok("Deleted trip #$id")
  }
}