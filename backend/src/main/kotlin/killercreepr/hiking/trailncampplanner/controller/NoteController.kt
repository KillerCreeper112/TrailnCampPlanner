package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.dto.CreateNoteRequest
import killercreepr.hiking.trailncampplanner.dto.NoteDto
import killercreepr.hiking.trailncampplanner.dto.UpdateNoteRequest
import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import killercreepr.hiking.trailncampplanner.service.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/notes")
class NoteController(
  val noteService: NoteService
) {
  @PostMapping("/trips/{tripId}")
  fun createTripNote(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable tripId: Long,
    @RequestBody request: CreateNoteRequest
  ): ResponseEntity<NoteDto> {
    return ResponseEntity(
      noteService.createTripNote(user.id, tripId, request),
      HttpStatus.CREATED
    )
  }

  @PostMapping("/routes/{routeId}")
  fun createRouteNote(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable routeId: Long,
    @RequestBody request: CreateNoteRequest
  ): ResponseEntity<NoteDto> {
    return ResponseEntity(
      noteService.createRouteNote(user.id, routeId, request),
      HttpStatus.CREATED
    )
  }

  @PutMapping("/note/{id}")
  fun updateNote(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable id: Long,
    @RequestBody dto: UpdateNoteRequest
  ): ResponseEntity<NoteDto> = ResponseEntity.ok(
    noteService.updateNote(id, user.id, dto)
  )

  @PostMapping("/route_points/{routePointId}")
  fun createRoutePointNote(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable routePointId: Long,
    @RequestBody request: CreateNoteRequest
  ): ResponseEntity<NoteDto> {
    return ResponseEntity(
      noteService.createRoutePointNote(user.id, routePointId, request),
      HttpStatus.CREATED
    )
  }

  @GetMapping("/map/{tripId}")
  fun getTripMapNotes(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable tripId: Long
  ): ResponseEntity<List<NoteDto>> = ResponseEntity.ok(
    noteService.getMapNotes(user.id, tripId)
  )

  @GetMapping("/trip/{tripId}")
  fun getTripNotes(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable tripId: Long
  ): ResponseEntity<List<NoteDto>> = ResponseEntity.ok(
    noteService.getTripNotes(user.id, tripId)
  )

  @GetMapping("/route/{routeId}")
  fun getRouteNotes(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable routeId: Long
  ): ResponseEntity<List<NoteDto>> = ResponseEntity.ok(
    noteService.getRouteNotes(user.id, routeId)
  )

  @GetMapping("/route_points/{routePointId}")
  fun getRoutePointNotes(
    @AuthenticationPrincipal user: PrincipalUser,
    @PathVariable routePointId: Long
  ): ResponseEntity<List<NoteDto>> = ResponseEntity.ok(
    noteService.getRoutePointNotes(user.id, routePointId)
  )
}