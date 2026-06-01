package killercreepr.hiking.trailncampplanner.dto

import killercreepr.hiking.trailncampplanner.entity.TripDifficulty
import java.time.Instant
import java.time.LocalDate

data class TripDto(
  val createdAt: Instant,
  val id: Long,
  val name: String,
  val description: String?,
  val startDate: LocalDate?,
  val endDate: LocalDate?,
  val difficulty: TripDifficulty,
  val routes: List<RouteDto>
)

data class UpdateTripRequest(
  val name: String,
  val description: String?,
  val startDate: LocalDate?,
  val endDate: LocalDate?,
  val difficulty: TripDifficulty,
)

data class CreateTripRequest(
  val name: String,
  val description: String?
)