package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateNoteRequest
import killercreepr.hiking.trailncampplanner.dto.NoteDto
import killercreepr.hiking.trailncampplanner.dto.UpdateNoteRequest

interface NoteService {
  fun createTripNote(
    userId: Long,
    tripId: Long,
    dto: CreateNoteRequest
  ): NoteDto

  fun createRouteNote(
    userId: Long,
    routeId: Long,
    dto: CreateNoteRequest
  ): NoteDto

  fun updateNote(
    id: Long,
    createdById: Long,
    dto: UpdateNoteRequest
  ): NoteDto

  fun createRoutePointNote(
    userId: Long,
    routePointId: Long,
    dto: CreateNoteRequest
  ): NoteDto

  fun deleteNote(id: Long, userId: Long)

  fun getMapNotes(userId: Long, tripId: Long): List<NoteDto>
  fun getTripNotes(userId: Long, tripId: Long): List<NoteDto>
  fun getRouteNotes(userId: Long, routeId: Long): List<NoteDto>
  fun getRoutePointNotes(userId: Long, routePointId: Long): List<NoteDto>
}