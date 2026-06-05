package killercreepr.hiking.trailncampplanner.dto

data class RouteDto(
  val id: Long,
  val name: String?,
  val points: List<RoutePointDto>
)

data class CreateRouteRequest(
  val name: String?
)