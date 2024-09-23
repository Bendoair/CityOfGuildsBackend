package hu.bme.aut.backend.cityofguildsbackend.services.impl

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.repositories.IUserRepository
import hu.bme.aut.backend.cityofguildsbackend.services.IUserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: IUserRepository,
): IUserService {
    override fun save(user: UserEntity): UserEntity {
        return userRepository.save(user)

    }

    override fun getUserById(id: String): UserEntity? {
        return userRepository.findByIdOrNull(id)
    }

}