package hu.bme.aut.backend.cityofguildsbackend.services

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import org.springframework.stereotype.Service
import kotlin.reflect.jvm.internal.impl.util.AbstractArrayMapOwner

@Service
interface IPointService {
    fun getAllPoints(): List<PointEntity>
    fun getAllUserPoints(owner: UserEntity): List<PointEntity>
    fun capturePoint(pointId: String, newOwner: UserEntity): Boolean
    fun addPoint(newPointEntity: PointEntity): PointEntity
    fun getPointById(pointId: String): PointEntity?
}