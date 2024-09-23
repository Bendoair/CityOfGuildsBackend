package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PointController (
    private val pointService: PointService
){
    @GetMapping("points/getAll")
    fun getPoints() : ResponseEntity<List<PointEntity>>{
        return ResponseEntity.ok(pointService.getAllPoints())
    }

    @GetMapping("points/get/{pointId}")
    fun getPoints(@PathVariable("pointId") pointId : String) : ResponseEntity<PointEntity?>{
        val foundPoint = pointService.getPointById(pointId)?:
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(foundPoint)
    }

    @PutMapping("points/capture/{pointId}")
    fun capturePoint(@PathVariable("pointId") pointId : String, @RequestBody newOwner:UserEntity) : ResponseEntity<Boolean>{
        if(pointService.capturePoint(pointId, newOwner)){
            return ResponseEntity.ok().body(true)
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("points/addNewPoint")
    fun addNewPoint(@RequestBody body:PointEntity) : ResponseEntity<PointEntity>{
        return ResponseEntity.ok().body(pointService.addPoint(body))
    }



}