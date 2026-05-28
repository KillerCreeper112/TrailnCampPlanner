package killercreepr.hiking.trailncampplanner.dto

data class RouteDto(
  val id: Long,
  val points: List<RoutePointDto>
)

class CreateRouteRequest