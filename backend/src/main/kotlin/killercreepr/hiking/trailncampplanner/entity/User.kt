package killercreepr.hiking.trailncampplanner.entity

import jakarta.persistence.*

@Entity
@Table(name="users")
class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0L,
  @Column(nullable = false, unique = true)
  var name: String = "",
  var password: String = "",
  var email: String = "",

  @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
  var trips: MutableList<Trip> = mutableListOf()
)

data class PrincipalUser(
  val id: Long,
  val name: String
)