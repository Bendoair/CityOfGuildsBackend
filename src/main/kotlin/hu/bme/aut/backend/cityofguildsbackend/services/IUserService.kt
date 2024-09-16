package hu.bme.aut.backend.cityofguildsbackend.services

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import org.springframework.stereotype.Service

@Service
interface  IUserService {
    fun save(user:UserEntity):UserEntity
    fun getUserById(id: String): UserEntity?
}