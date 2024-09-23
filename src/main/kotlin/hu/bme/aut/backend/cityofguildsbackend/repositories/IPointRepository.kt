package hu.bme.aut.backend.cityofguildsbackend.repositories

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface IPointRepository : JpaRepository<PointEntity, String>{
    fun findByOwner(owner: UserEntity): List<PointEntity>
    @Modifying
    @Transactional
    //This SQL returns the number of rows affected, we are happy, if it's 1
    @Query("UPDATE PointEntity p SET p.owner = :newOwner WHERE p.id = :pointId")
    fun updatePointOwner(@Param("pointId") pointId: String, @Param("newOwner") newOwner: UserEntity): Int


}