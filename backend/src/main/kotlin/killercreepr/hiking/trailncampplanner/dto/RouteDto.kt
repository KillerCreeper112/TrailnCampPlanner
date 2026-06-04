package killercreepr.hiking.trailncampplanner.dto

data class RouteDto(
  val id: Long,
  val name: String,
  val points: List<RoutePointDto>
)

class CreateRouteRequest