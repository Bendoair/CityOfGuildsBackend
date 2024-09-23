package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(
    private val userService: UserService,
    private val pointService: PointService,
) {
    @PutMapping("/users/addNewUser")
    fun addNewUser(@RequestBody user: UserEntity): ResponseEntity<UserEntity> {
        val result = userService.save(user)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/users/usersPoints/{userId}")
    fun getUserPoints(@PathVariable userId: String): ResponseEntity<List<PointEntity>> {
        val user = userService.getUserById(userId)
            ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyList<PointEntity>())
        return ResponseEntity.ok().body(pointService.getAllUserPoints(user))
    }
}