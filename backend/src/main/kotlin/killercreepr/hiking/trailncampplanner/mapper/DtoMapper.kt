package killercreepr.hiking.trailncampplanner.mapper

import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.dto.UserDto
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.entity.User

fun User.mapToDto(): UserDto = UserDto(id, name, email)
fun UserDto.mapToEntity(password: String): User = User(id, name, password, email)

fun RoutePointDto.mapToEntity(route: Route): RoutePoint = RoutePoint(id, latitude, longitude, orderIndex, route)
fun RoutePoint.mapToDto(): RoutePointDto = RoutePointDto(id, latitude, longitude, orderIndex)

fun Route.mapToDto(): RouteDto = RouteDto(id, points.map { it.mapToDto() })
fun RouteDto.mapToEntity(trip: Trip): Route = Route(id, trip = trip)

fun Trip.mapToDto(): TripDto = TripDto(
  createdAt, id, name, description, startDate, endDate, difficulty, routes.map { it.mapToDto() }
)