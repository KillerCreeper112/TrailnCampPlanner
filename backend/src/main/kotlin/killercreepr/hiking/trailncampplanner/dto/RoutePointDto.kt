package killercreepr.hiking.trailncampplanner.dto

data class RoutePointDto(
  val id: Long = 0L,
  val latitude: Double,
  val longitude: Double,
  val orderIndex: Int
)

data class CreateRoutePointRequest(
  val latitude: Double,
  val longitude: Double,
  val orderIndex: Int
)