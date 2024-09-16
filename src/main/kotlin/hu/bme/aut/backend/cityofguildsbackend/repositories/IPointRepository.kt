package hu.bme.aut.backend.cityofguildsbackend.repositories

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IPointRepository : JpaRepository<PointEntity, String>