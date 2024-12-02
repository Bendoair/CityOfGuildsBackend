package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.ILeaderboardService
import hu.bme.aut.backend.cityofguildsbackend.services.IPointService
import hu.bme.aut.backend.cityofguildsbackend.services.IUserService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.LeaderboardService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: IUserService,
    private val pointService: IPointService,
    private val leaderboardService: ILeaderboardService
) {
    @PutMapping("/addNewUser")
    fun addNewUser(@RequestBody user: UserEntity): ResponseEntity<UserEntity> {
        val result = userService.save(user)
        return ResponseEntity.created(URI("/users/${result.id}")).body(result)
    }

    @GetMapping("/usersPoints/{userId}")
    fun getUserPoints(@PathVariable userId: String): ResponseEntity<List<PointEntity>> {
        val user = userService.getUserById(userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyList<PointEntity>())

        return ResponseEntity.ok().body(pointService.getAllUserPoints(user.copy(password = "Haha, no")))
    }

    @GetMapping("/leaderboard")
    fun getLeaderboard(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok(leaderboardService.getTopTen())
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): ResponseEntity<UserEntity> {
        val user = userService.getUserById(userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(UserEntity())
        return ResponseEntity.ok(user)
    }
}