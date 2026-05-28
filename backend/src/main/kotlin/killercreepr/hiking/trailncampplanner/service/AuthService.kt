package killercreepr.hiking.trailncampplanner.service

import killercreepr.hiking.trailncampplanner.dto.LoginRequest

// In the future, we can make a refresh token system that involves backend
// for improved security.
interface AuthService {
  fun login(request: LoginRequest): String
}