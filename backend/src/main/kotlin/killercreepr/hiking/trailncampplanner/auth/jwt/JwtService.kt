package killercreepr.hiking.trailncampplanner.auth.jwt

import killercreepr.hiking.trailncampplanner.entity.User

interface JwtService {
  fun generateToken(user: User): String
  fun extractUsername(token: String): String
  fun isValid(token: String): Boolean
}