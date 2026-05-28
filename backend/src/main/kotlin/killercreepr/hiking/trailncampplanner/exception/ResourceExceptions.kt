package killercreepr.hiking.trailncampplanner.exception

class ResourceNotFoundException(
  msg: String
): RuntimeException(msg)

class ResourceAlreadyExistsException(
  msg: String
): RuntimeException(msg)