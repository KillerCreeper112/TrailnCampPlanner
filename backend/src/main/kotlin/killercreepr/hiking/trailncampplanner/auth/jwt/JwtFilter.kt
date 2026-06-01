package killercreepr.hiking.trailncampplanner.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
  val jwtService: JwtService,
  val userRepository: UserRepository
): OncePerRequestFilter() {
  private val authHeaderPrefix = "Bearer "
  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {

    val authHeader = request.getHeader("Authorization")

    if (authHeader == null || !authHeader.startsWith(authHeaderPrefix)) {
      filterChain.doFilter(request, response)
      return
    }

    val token = authHeader.substring(authHeaderPrefix.length)
    if (!jwtService.isValid(token)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
      return
    }

    val userId = jwtService.extractUsername(token)
    val user = userRepository.findById(userId.toLong()).orElse(null)
    if (user != null) {
      val auth = UsernamePasswordAuthenticationToken(
        PrincipalUser(user.id, user.name),
        null,
        emptyList()
      )
      SecurityContextHolder.getContext().authentication = auth
    }
    filterChain.doFilter(request, response)
  }
}