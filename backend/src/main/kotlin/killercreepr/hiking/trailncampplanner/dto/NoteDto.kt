package killercreepr.hiking.trailncampplanner.dto

import java.time.Instant

data class NoteDto(
  val id: Long,
  val content: String,
  val latitude: Double?,
  val longitude: Double?,
  val icon: String?,
  val createdBy: UserDto,
  val trip: TripDto,
  val route: RouteDto?,
  val routePoint: RoutePointDto?
)

data class NoteCommentDto(
  val id: Long,
  val content: String,
  val createdAt: Instant
)

data class CreateNoteRequest(
  val content: String,
  val latitude: Double?,
  val longitude: Double?,
  val icon: String?,

)