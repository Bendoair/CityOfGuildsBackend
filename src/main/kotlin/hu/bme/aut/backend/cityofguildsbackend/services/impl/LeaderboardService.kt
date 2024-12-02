package hu.bme.aut.backend.cityofguildsbackend.services.impl

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.repositories.IUserRepository
import hu.bme.aut.backend.cityofguildsbackend.services.ILeaderboardService
import hu.bme.aut.backend.cityofguildsbackend.services.IPointService
import hu.bme.aut.backend.cityofguildsbackend.services.IUserService
import org.springframework.stereotype.Service

@Service
class LeaderboardService(
    private val pointService: IPointService,
    private val userService: IUserService,
    private val userRepository: IUserRepository
) : ILeaderboardService {
    override fun updateLeaderboard() {
        val points = pointService.getAllPoints()
        //Store how much we need to increment each user
        val usersAndPointsMap = mutableMapOf<UserEntity, Int>()
        points.forEach {
            if(it.owner != null){
                if(usersAndPointsMap.containsKey(it.owner)){
                    usersAndPointsMap[it.owner!!] = usersAndPointsMap[it.owner]!! +1
                }else{
                    usersAndPointsMap[it.owner!!] = 1
                }
            }
        }

        //Save in database only once
        usersAndPointsMap.forEach {
            val newUser = UserEntity(it.key.id, it.key.numberOfPoints + it.value, it.key.isDeveloper)
            userService.save(newUser)
        }
    }

    override fun getTopTen(): List<UserEntity> {
        return userRepository.findTop10ByOrderByNumberOfPointsDesc().map { it?.copy(password = "")?:UserEntity() }
    }
}