package killercreepr.hiking.trailncampplanner.dto

data class UserDto(
  val id: Long,
  val name: String,
  val email: String?
)

data class CreateUserRequest(
  val name: String,
  val password: String,
  val email: String?,
)