package killercreepr.hiking.trailncampplanner.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "jwt")
data class JwtConfig(
  val secret: String,
  val expirationMilliseconds: Long
)