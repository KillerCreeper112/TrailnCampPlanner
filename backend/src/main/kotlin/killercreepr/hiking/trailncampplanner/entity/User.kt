package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*

@Entity
@Table(name="users")
class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0L,
  @Column(nullable = false, unique = true)
  val name: String,
  val password: String,
  val email: String?,

  @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
  val trips: MutableList<Trip> = mutableListOf()
)