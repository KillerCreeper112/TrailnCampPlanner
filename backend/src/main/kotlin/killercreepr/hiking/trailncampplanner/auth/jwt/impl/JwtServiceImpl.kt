package killercreepr.hiking.trailncampplanner.auth.jwt.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import killercreepr.hiking.trailncampplanner.auth.jwt.JwtService
import killercreepr.hiking.trailncampplanner.config.JwtProperties
import killercreepr.hiking.trailncampplanner.entity.User
import java.util.*

class JwtServiceImpl(
  val config: JwtProperties,
) : JwtService {
  private val secretKey by lazy { Keys.hmacShaKeyFor(config.secret.toByteArray()) }
  private val expirationMilliseconds by lazy { config.expirationMilliseconds }

  override fun generateToken(user: User): String = Jwts.builder()
    .subject(user.id.toString())
    .claim("email", user.email)
    .issuedAt(Date())
    .expiration(Date(System.currentTimeMillis() + expirationMilliseconds))
    .signWith(secretKey)
    .compact()

  override fun extractUsername(token: String): String = Jwts.parser()
    .verifyWith(secretKey)
    .build()
    .parseSignedClaims(token)
    .payload
    .subject

  override fun isValid(token: String): Boolean = try {
    Jwts.parser()
      .verifyWith(secretKey)
      .build()
      .parseSignedClaims(token)
    true
  } catch (e: Exception) {
    false
  }
}