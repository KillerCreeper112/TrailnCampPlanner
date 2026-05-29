package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate

@Entity
@EntityListeners(AuditingEntityListener::class)
class Trip(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,
  var name: String = "",
  var description: String? = null,
  var startDate: LocalDate? = null,
  var endDate: LocalDate? = null,
  @Enumerated(EnumType.STRING)
  var difficulty: TripDifficulty = TripDifficulty.MEDIUM,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "trip", cascade = [CascadeType.ALL], orphanRemoval = true)
  var routes: MutableList<Route> = mutableListOf(),
){
  @CreatedDate
  lateinit var createdAt: Instant

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  lateinit var user: User
}

enum class TripDifficulty{
  EASY, MEDIUM, HARD
}