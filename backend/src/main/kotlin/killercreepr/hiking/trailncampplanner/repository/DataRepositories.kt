package killercreepr.hiking.trailncampplanner.repository

import killercreepr.hiking.trailncampplanner.entity.Route
import killercreepr.hiking.trailncampplanner.entity.RoutePoint
import killercreepr.hiking.trailncampplanner.entity.Trip
import killercreepr.hiking.trailncampplanner.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>{
  fun findByName(name: String): User?
  fun findByEmail(email: String): User?

  fun existsByName(name: String): Boolean
  fun existsByEmail(email: String): Boolean
}
interface TripRepository : JpaRepository<Trip, Long>{
  fun findByName(name: String): Trip?

  fun existsByName(name: String): Boolean
}
interface RouteRepository : JpaRepository<Route, Long>
interface RoutePointRepository : JpaRepository<RoutePoint, Long>