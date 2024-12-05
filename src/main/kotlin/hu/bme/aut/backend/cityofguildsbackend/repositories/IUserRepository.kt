package hu.bme.aut.backend.cityofguildsbackend.repositories

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository : JpaRepository<UserEntity, String>{

    @Modifying
    @Transactional
    //This SQL returns the number of rows affected, we are happy, if it's 1
    @Query("UPDATE UserEntity u SET u.numberOfPoints = :newNumberOfPoints WHERE u.id = :user_id")
    fun updateById(user_id: String, newNumberOfPoints: Int): Int

    fun findByEmail(email: String): UserEntity?

    fun findTop10ByOrderByNumberOfPointsDesc(): List<UserEntity?>
}