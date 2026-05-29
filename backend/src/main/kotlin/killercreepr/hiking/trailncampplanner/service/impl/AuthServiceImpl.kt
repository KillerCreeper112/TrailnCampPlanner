package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.auth.jwt.JwtService
import killercreepr.hiking.trailncampplanner.dto.LoginRequest
import killercreepr.hiking.trailncampplanner.exception.PasswordNotMatchingException
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.AuthService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
  val userRepository: UserRepository,
  val passwordEncoder: PasswordEncoder,
  val jwtService: JwtService
): AuthService {
  override fun login(request: LoginRequest): String {
    val user = userRepository.findByEmail(request.email)
      ?: throw ResourceNotFoundException("User with email ${request.email} not found")
    if(!passwordEncoder.matches(request.password, user.password))
      throw PasswordNotMatchingException("Username or password does not match")
    return jwtService.generateToken(user)
  }
}