package killercreepr.hiking.trailncampplanner.repository

import killercreepr.hiking.trailncampplanner.entity.Note
import killercreepr.hiking.trailncampplanner.entity.NoteType
import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository: JpaRepository<Note, Long>{
  fun findByIdAndCreatedById(id: Long, createdById: Long): Note?
  fun findAllByTripIdAndType(tripId: Long, type: NoteType): List<Note>
  fun findAllByTripId(tripId: Long): List<Note>
  fun findAllByRouteId(routeId: Long): List<Note>
  fun findAllByRoutePointId(routePointId: Long): List<Note>
  fun findAllByRoutePointIdAndRoutePointRouteTripUserId(routePointId: Long, routePointRouteTripUserId: Long): List<Note>
  fun findAllByTripIdAndLatitudeIsNotNullAndLongitudeIsNotNull(tripId: Long): List<Note>

  fun existsByIdAndCreatedById(id: Long, createById: Long): Boolean
}

interface UserRepository: JpaRepository<User, Long>{
  fun findByName(name: String): User?
  fun findByEmail(email: String): User?

  fun existsByName(name: String): Boolean
  fun existsByEmail(email: String): Boolean
}
interface TripRepository : JpaRepository<Trip, Long>{
  fun findByName(name: String): Trip?

  fun existsByName(name: String): Boolean

  fun existsByIdAndUserId(id: Long, userId: Long): Boolean
  fun findByIdAndUserId(id: Long, userId: Long): Trip?
}
interface RouteRepository : JpaRepository<Route, Long>{
  fun findByIdAndTripUserId(id: Long, userId: Long): Route?
  fun existsByIdAndTripUserId(id: Long, userId: Long): Boolean
}
interface RoutePointRepository : JpaRepository<RoutePoint, Long>{
  fun findByIdAndRouteTripUserId(id: Long, userId: Long): RoutePoint?
  fun existsByIdAndRouteTripId(id: Long, userId: Long): Boolean
}