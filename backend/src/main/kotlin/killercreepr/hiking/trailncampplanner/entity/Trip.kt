package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDate

@Entity
@EntityListeners(AuditingEntityListener::class)
class Trip(
  @CreatedDate
  val createdAt: Instant,

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,
  val name: String,
  val description: String? = null,
  val startDate: LocalDate? = null,
  val endDate: LocalDate? = null,
  @Enumerated(EnumType.STRING)
  val difficulty: TripDifficulty = TripDifficulty.MEDIUM,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  val user: User,

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "route", cascade = [CascadeType.ALL], orphanRemoval = true)
  val routes: MutableList<Route> = mutableListOf(),
)

enum class TripDifficulty{
  EASY, MEDIUM, HARD
}