package killercreepr.hiking.trailncampplanner.mapper

import killercreepr.hiking.trailncampplanner.dto.NoteDto
import killercreepr.hiking.trailncampplanner.dto.RouteDto
import killercreepr.hiking.trailncampplanner.dto.RoutePointDto
import killercreepr.hiking.trailncampplanner.dto.TripDto
import killercreepr.hiking.trailncampplanner.dto.UserDto
import killercreepr.hiking.trailncampplanner.entity.Note
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.entity.User

fun User.mapToDto(): UserDto = UserDto(id, name, email)
fun UserDto.mapToEntity(password: String): User = User(id, name, password, email)

fun RoutePointDto.mapToEntity(route: Route): RoutePoint = RoutePoint(id, latitude, longitude, orderIndex).also{it.route = route}
fun RoutePoint.mapToDto(): RoutePointDto = RoutePointDto(id, latitude, longitude, orderIndex)

fun Route.mapToDto(): RouteDto = RouteDto(id, name,points.map { it.mapToDto() })
fun RouteDto.mapToEntity(trip: Trip): Route = Route(id,name).also { it.trip = trip }

fun Trip.mapToDto(): TripDto = TripDto(
  createdAt, id, name, description, startDate, endDate, difficulty, routes.map { it.mapToDto() }
)

fun Note.mapToDto(): NoteDto = NoteDto(
  id,
  type,
  content, latitude, longitude, icon,
  createdBy.mapToDto(),
  trip.mapToDto(),
  route?.mapToDto(),
  routePoint?.mapToDto()
)
fun NoteDto.mapToEntity(
  createdBy: User,
  trip: Trip,
  route: Route?,
  routePoint: RoutePoint?
): Note = Note(
  id,
  latitude = latitude,
  longitude = longitude,
  content = content,
  icon = icon,
).also{
  it.createdBy = createdBy
  it.trip = trip
  it.route = route
  it.routePoint = routePoint
}
