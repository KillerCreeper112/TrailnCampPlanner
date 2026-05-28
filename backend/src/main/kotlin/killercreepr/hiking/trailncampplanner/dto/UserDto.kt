package killercreepr.hiking.trailncampplanner.dto

data class UserDto(
  val id: Long,
  val name: String,
  val email: String //? email was originally going to be optional but it's easier to just require it
)

data class CreateUserRequest(
  val name: String,
  val password: String,
  val email: String,
)