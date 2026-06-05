package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.springframework.data.annotation.CreatedDate
import java.time.Instant

@Entity
class Note(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,

  @Enumerated(EnumType.STRING)
  var type: NoteType = NoteType.TRIP,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "route_id")
  var route: Route? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "route_point_id")
  var routePoint: RoutePoint? = null,

  var latitude: Double? = null,
  var longitude: Double? = null,

  var content: String = "",

  var icon: String? = null
){
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "trip_id", nullable = false)
  lateinit var trip: Trip

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", nullable = false)
  lateinit var createdBy: User
}

enum class NoteType{
  TRIP,
  MAP,
  ROUTE,
  ROUTE_POINT
}

@Entity
class NoteComment(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,
  var content: String = ""
){
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id", nullable = false)
  lateinit var note: Note

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by", nullable = false)
  lateinit var createdBy: User

  @CreatedDate
  lateinit var createdAt: Instant
}

@Entity
class NoteReaction(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,
  var emoji: String = "\uD83D\uDC4D"
){
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_id", nullable = false)
  lateinit var note: Note

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  lateinit var user: User
}