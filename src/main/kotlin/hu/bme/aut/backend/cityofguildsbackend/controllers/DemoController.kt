package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.IUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class DemoController(
    private val userService: IUserService
) {
    @GetMapping("/")
    fun index(@RequestParam("name") name:String):String{return "Hello $name"}

    @PostMapping("/save")
    fun save(@RequestBody user:UserEntity):UserEntity{
        return userService.save(user)
    }
    @GetMapping("/users/{id}")
    fun getUser(@PathVariable id:String):ResponseEntity<UserEntity?>{
        val user = userService.getUserById(id)
        if(user != null){
            return ResponseEntity(user, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)

    }
}