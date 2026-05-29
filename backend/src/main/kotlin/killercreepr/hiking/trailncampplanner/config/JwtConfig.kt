package killercreepr.hiking.trailncampplanner.config

import killercreepr.hiking.trailncampplanner.auth.jwt.JwtService
import killercreepr.hiking.trailncampplanner.auth.jwt.impl.JwtServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfig {
  @Bean
  fun jwtService(jwtProperties: JwtProperties): JwtService = JwtServiceImpl(jwtProperties)
}