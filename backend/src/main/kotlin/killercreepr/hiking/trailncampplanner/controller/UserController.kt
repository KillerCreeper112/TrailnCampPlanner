package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.dto.CreateUserRequest
import killercreepr.hiking.trailncampplanner.dto.UserDto
import killercreepr.hiking.trailncampplanner.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
  val userService: UserService
) {
  @PostMapping
  fun createUser(@RequestBody dto: CreateUserRequest): ResponseEntity<UserDto> =
    ResponseEntity(userService.createUser(dto), HttpStatus.CREATED)

  @GetMapping("{id}")
  fun getUserById(@PathVariable id: Long): ResponseEntity<UserDto> =
    ResponseEntity.ok(userService.findUserById(id))

  @GetMapping
  fun getAllUsers(): ResponseEntity<List<UserDto>> = ResponseEntity.ok(userService.findAllUsers())

  @DeleteMapping("{id}")
  fun deleteUserById(@PathVariable id: Long): ResponseEntity<String>{
    userService.deleteUser(id)
    return ResponseEntity.ok("Deleted user #$id")
  }
}