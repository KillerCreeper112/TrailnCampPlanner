package killercreepr.hiking.trailncampplanner.auth

import killercreepr.hiking.trailncampplanner.entity.User
import org.springframework.security.core.context.SecurityContextHolder

fun extractPrincipalUser(): User =
  SecurityContextHolder.getContext().authentication?.principal as User