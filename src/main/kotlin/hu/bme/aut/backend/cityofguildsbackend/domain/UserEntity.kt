package hu.bme.aut.backend.cityofguildsbackend.domain

import jakarta.persistence.*

@Entity
data class UserEntity(
    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String = "",

    @Column(name = "numberOfPoints")
    var numberOfPoints: Int  = 0,

    @Column(name ="isDeveloper")
    var isDeveloper: Boolean = false,

    @Column(name = "email", unique = true, nullable = false)
    val email: String = "",

    //Hashed Password
    @Column(name = "password")
    val password: String = "",


)
