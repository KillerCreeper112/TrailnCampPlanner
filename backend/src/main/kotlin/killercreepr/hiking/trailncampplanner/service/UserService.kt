package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.CreateUserRequest
import killercreepr.hiking.trailncampplanner.dto.UserDto

interface UserService {
  fun createUser(dto: CreateUserRequest): UserDto
  fun findUserById(id: Long): UserDto
  fun findUserByName(name: String): UserDto
  fun findUserByEmail(email: String): UserDto

  fun findAllUsers(): List<UserDto>
  fun deleteUser(id: Long)
}