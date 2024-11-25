package hu.bme.aut.backend.cityofguildsbackend.services

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.repositories.IUserRepository
import org.springframework.stereotype.Service


interface IAuthService {

    //Return the access token for given user if auth is successful
    fun authenticate(email : String, password : String) : String

    fun register(email : String, password : String) : String

    fun getUserByToken(token: String) : UserEntity?

}