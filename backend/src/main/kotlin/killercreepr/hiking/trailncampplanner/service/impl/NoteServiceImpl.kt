package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateNoteRequest
import killercreepr.hiking.trailncampplanner.dto.NoteDto
import killercreepr.hiking.trailncampplanner.dto.UpdateNoteRequest
import killercreepr.hiking.trailncampplanner.entity.Note
import killercreepr.hiking.trailncampplanner.entity.NoteType
import killercreepr.hiking.trailncampplanner.entity.User
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.NoteRepository
import killercreepr.hiking.trailncampplanner.repository.RoutePointRepository
import killercreepr.hiking.trailncampplanner.repository.RouteRepository
import killercreepr.hiking.trailncampplanner.repository.TripRepository
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.NoteService
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Service

@Service
class NoteServiceImpl(
  val noteRepository: NoteRepository,
  val userRepository: UserRepository,
  val tripRepository: TripRepository,
  val routeRepository: RouteRepository,
  val routePointRepository: RoutePointRepository
): NoteService {
  private fun user(userId: Long): User = userRepository.findById(userId).orElseThrow {
    throw ResourceNotFoundException("No user with id $userId")
  }

  override fun createTripNote(
    userId: Long,
    tripId: Long,
    dto: CreateNoteRequest
  ): NoteDto {
    val user = user(userId)
    val trip = tripRepository.findByIdAndUserId(tripId, userId) ?:
    throw AccessDeniedException("Access denied")

    if((dto.latitude == null) != (dto.longitude == null)){
      throw IllegalArgumentException("Invalid latitude and longitude values")
    }

    val note = Note().also {
      it.type = if(dto.latitude != null) NoteType.MAP else NoteType.TRIP
      it.content = dto.content
      it.longitude = dto.longitude
      it.latitude = dto.latitude
      it.icon = dto.icon

      it.createdBy = user
      it.trip = trip
    }
    return noteRepository.saveAndFlush(note).mapToDto()
  }

  override fun createRouteNote(
    userId: Long,
    routeId: Long,
    dto: CreateNoteRequest
  ): NoteDto {
    val user = user(userId)
    val route = routeRepository.findByIdAndTripUserId(routeId, userId) ?:
    throw AccessDeniedException("Access denied")
    val note = Note().also {
      it.type = NoteType.ROUTE
      it.content = dto.content
      it.longitude = dto.longitude
      it.latitude = dto.latitude
      it.icon = dto.icon

      it.createdBy = user
      it.trip = route.trip
      it.route = route
    }
    return noteRepository.save(note).mapToDto()
  }

  override fun updateNote(
    id: Long,
    createdById: Long,
    dto: UpdateNoteRequest
  ): NoteDto {
    val note = noteRepository.findByIdAndCreatedById(id, createdById)
      ?: throw AccessDeniedException("Access denied")
    return noteRepository.save(note.also {
      it.icon = dto.icon ?: it.icon
      it.content = dto.content ?: it.content
      it.latitude = dto.latitude ?: it.latitude
      it.longitude = dto.longitude ?: it.longitude
    }).mapToDto()
  }

  override fun createRoutePointNote(
    userId: Long,
    routePointId: Long,
    dto: CreateNoteRequest
  ): NoteDto {
    val user = user(userId)
    val routePoint = routePointRepository.findByIdAndRouteTripUserId(routePointId, userId) ?:
    throw AccessDeniedException("Access denied")
    val note = Note().also {
      it.type = NoteType.ROUTE_POINT
      it.content = dto.content
      it.longitude = dto.longitude
      it.latitude = dto.latitude
      it.icon = dto.icon

      it.createdBy = user
      it.trip = routePoint.route.trip
      it.routePoint = routePoint
    }
    return noteRepository.save(note).mapToDto()
  }

  override fun deleteNote(id: Long, userId: Long) {
    if(!noteRepository.existsByIdAndCreatedById(id, userId))
      throw AccessDeniedException("Access denied")
    noteRepository.deleteById(id)
  }

  override fun getMapNotes(
    userId: Long,
    tripId: Long
  ): List<NoteDto> {
    if(!tripRepository.existsByIdAndUserId(tripId, userId))
      throw AccessDeniedException("Access denied")
    return noteRepository.findAllByTripIdAndType(tripId, NoteType.MAP).map { it.mapToDto() }
  }

  override fun getTripNotes(
    userId: Long,
    tripId: Long
  ): List<NoteDto> {
    if(!tripRepository.existsByIdAndUserId(tripId, userId))
      throw AccessDeniedException("Access denied")
    return noteRepository.findAllByTripIdAndType(tripId, NoteType.TRIP).map { it.mapToDto() }
  }

  override fun getRouteNotes(
    userId: Long,
    routeId: Long
  ): List<NoteDto> {
    if(!routeRepository.existsByIdAndTripUserId(routeId, userId))
      throw AccessDeniedException("Access denied")
    return noteRepository.findAllByRouteId(routeId).map { it.mapToDto() }
  }

  override fun getRoutePointNotes(
    userId: Long,
    routePointId: Long
  ): List<NoteDto> {
    /*if(!routePointRepository.existsByIdAndRouteTripId(routePointId, userId))
      throw AccessDeniedException("Access denied")*/
    return noteRepository.findAllByRoutePointIdAndRoutePointRouteTripUserId(routePointId, userId).map { it.mapToDto() }
  }
}