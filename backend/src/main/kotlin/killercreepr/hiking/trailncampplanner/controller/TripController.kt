package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.auth.extractPrincipalUser
import killercreepr.hiking.trailncampplanner.dto.CreateTripRequest
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.service.TripService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/trips")
class TripController(
  val tripService: TripService
) {
  @PostMapping
  fun createTrip(@RequestBody dto: CreateTripRequest):
    ResponseEntity<TripDto>{
    val created = tripService.createTrip(dto)
    return ResponseEntity(created, HttpStatus.CREATED)
  }

  @GetMapping
  fun getTrips(): ResponseEntity<List<TripDto>>{
    return ResponseEntity.ok(tripService.getUserTrips())
  }

  @DeleteMapping("{id}")
  fun deleteTrip(
    @PathVariable("id") id: Long
  ): ResponseEntity<String>{
    val user = extractPrincipalUser()
    tripService.deleteTripIfOwner(id, user.id)
    return ResponseEntity.ok("Deleted trip #$id")
  }
}