package hu.bme.aut.backend.cityofguildsbackend.domain

import jakarta.persistence.*

@Entity
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    val id: String = "",

    @Column(name = "numberOfPoints")
    val numberOfPoints: Int  = 0
)
