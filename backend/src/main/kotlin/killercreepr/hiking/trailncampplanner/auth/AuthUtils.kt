package killercreepr.hiking.trailncampplanner.auth

import killercreepr.hiking.trailncampplanner.entity.PrincipalUser
import org.springframework.security.core.context.SecurityContextHolder

fun extractPrincipalUser(): PrincipalUser =
  SecurityContextHolder.getContext().authentication?.principal as PrincipalUser