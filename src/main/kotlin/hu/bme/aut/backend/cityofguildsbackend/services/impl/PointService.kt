package hu.bme.aut.backend.cityofguildsbackend.services.impl

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.repositories.IPointRepository
import hu.bme.aut.backend.cityofguildsbackend.repositories.IUserRepository
import hu.bme.aut.backend.cityofguildsbackend.services.IPointService
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime

@Service
class PointService(
    private val pointRepository: IPointRepository,
    private val userRepository: IUserRepository,
) : IPointService {
    override fun getAllPoints(): List<PointEntity> {
       return pointRepository.findAll()
    }

    override fun getAllUserPoints(owner : UserEntity): List<PointEntity> {
        return pointRepository.findByOwner(owner)
    }

    override fun capturePoint(pointId: String, newOwner: UserEntity): Boolean {
        //This returns the number of rows affected, which should be one
        if ( pointRepository.updatePointOwner(pointId, newOwner) == 1){
            val currentPoint = pointRepository.findById(pointId).get()
            val currentDateTime = LocalDateTime.now()
            val pointsGained = calculatePointEarned(currentPoint.captureDate, currentDateTime)
            currentPoint.captureDate = currentDateTime
            //TODO check if this increases the points of the owner IT DOES!!!! no TODO here, just happpppyyyy
            val owner = currentPoint.owner
            owner?.let{
                owner.numberOfPoints += pointsGained
                userRepository.save(it)
            }
            pointRepository.save(currentPoint)
            return true
        }
        return false
    }

    override fun addPoint(newPointEntity: PointEntity): PointEntity {
        return pointRepository.save(newPointEntity)
    }

    override fun getPointById(pointId: String): PointEntity? {
        return pointRepository.findById(pointId).orElse(null)
    }


}

fun calculatePointEarned( from: LocalDateTime, til : LocalDateTime): Int{
    val duration = Duration.between(from, til)
    return duration.toHours().toInt()
}