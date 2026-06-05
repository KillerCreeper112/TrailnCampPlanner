package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*

@Entity
class Route(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,

  var name: String? = null,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
  @OrderBy("orderIndex ASC")
  var points: MutableList<RoutePoint> = mutableListOf(),

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
  var notes: MutableList<Note> = mutableListOf()
){
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id", nullable = false)
  lateinit var trip: Trip
}

@Entity
class RoutePoint(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,

  var latitude: Double = 0.0,
  var longitude: Double = 0.0,
  var orderIndex: Int = 0,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "routePoint", cascade = [CascadeType.ALL], orphanRemoval = true)
  var notes: MutableList<Note> = mutableListOf()
){
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "route_id", nullable = false)
  lateinit var route: Route
}