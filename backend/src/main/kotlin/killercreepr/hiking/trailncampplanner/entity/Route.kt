package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*

@Entity
class Route(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
  @OrderBy("orderIndex ASC")
  val points: MutableList<RoutePoint> = mutableListOf(),

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id", nullable = false)
  val trip: Trip
)

@Entity
class RoutePoint(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,

  val latitude: Double,
  val longitude: Double,
  val orderIndex: Int,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "route_id", nullable = false)
  val route: Route
)