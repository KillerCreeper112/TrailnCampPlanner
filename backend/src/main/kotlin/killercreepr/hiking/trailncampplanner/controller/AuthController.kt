package killercreepr.hiking.trailncampplanner.controller

import killercreepr.hiking.trailncampplanner.auth.AuthResponse
import killercreepr.hiking.trailncampplanner.dto.LoginRequest
import killercreepr.hiking.trailncampplanner.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
  val authService: AuthService
) {

  @PostMapping("/login")
  fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
    val token = authService.login(request)
    return ResponseEntity.ok(AuthResponse(token))
  }

}