package killercreepr.hiking.trailncampplanner.service.impl

import killercreepr.hiking.trailncampplanner.dto.CreateUserRequest
import killercreepr.hiking.trailncampplanner.dto.UserDto
import killercreepr.hiking.trailncampplanner.entity.User
import killercreepr.hiking.trailncampplanner.exception.ResourceAlreadyExistsException
import killercreepr.hiking.trailncampplanner.exception.ResourceNotFoundException
import killercreepr.hiking.trailncampplanner.mapper.mapToDto
import killercreepr.hiking.trailncampplanner.repository.UserRepository
import killercreepr.hiking.trailncampplanner.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImpl(
  val userRepository: UserRepository
): UserService {
  @Autowired
  private var passwordEncoder: PasswordEncoder? = null

  override fun createUser(dto: CreateUserRequest): UserDto {
    if(userRepository.existsByName(dto.name)){
      throw ResourceAlreadyExistsException("User already exists with name ${dto.name}")
    }
    if(dto.email?.let{userRepository.existsByEmail(it)} == true){
      throw ResourceAlreadyExistsException("User already exists with email ${dto.email}")
    }

    val encodedPassword = passwordEncoder!!.encode(dto.password)!!
    val user = User(
      name = dto.name,
      email = dto.email,
      password = encodedPassword
    )
    return userRepository.save(user).mapToDto()
  }

  override fun findUserById(id: Long): UserDto = userRepository.findById(id)
    .orElseThrow { ResourceNotFoundException("User with ID $id not found") }.mapToDto()

  override fun findUserByName(name: String): UserDto = userRepository.findByName(name)?.mapToDto()
    ?: throw ResourceNotFoundException("User with name $name not found")

  override fun findUserByEmail(email: String): UserDto = userRepository.findByEmail(email)?.mapToDto()
    ?: throw ResourceNotFoundException("User with email $email not found")

  override fun findAllUsers(): List<UserDto> = userRepository.findAll().map { it.mapToDto() }

  override fun deleteUser(id: Long) {
    if(!userRepository.existsById(id)) throw ResourceNotFoundException("User with ID $id not found")
    userRepository.deleteById(id)
  }
}