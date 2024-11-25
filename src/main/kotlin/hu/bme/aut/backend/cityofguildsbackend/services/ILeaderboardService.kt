package hu.bme.aut.backend.cityofguildsbackend.services

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import org.springframework.stereotype.Service

@Service
interface ILeaderboardService {

    fun updateLeaderboard()
    fun getTopTen() : List<UserEntity>
    fun getTopX(howMany: Int) : List<UserEntity>
}