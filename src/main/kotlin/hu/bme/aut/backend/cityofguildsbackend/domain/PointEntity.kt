package hu.bme.aut.backend.cityofguildsbackend.domain

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
data class PointEntity(
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    val id: String = "",

    @Column(name="guildName")
    val guildname: String = "",

    @Column(name="coordinateX")
    val coordinateX:Float = 0.0f,

    @Column(name="coordinateY")
    val coordinateY:Float = 0.0f,

    @Column(name="captureDate")
    var captureDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.EAGER, cascade = [(CascadeType.ALL)])
    @JoinColumn(name = "user_id")
    var owner: UserEntity? = null
)
