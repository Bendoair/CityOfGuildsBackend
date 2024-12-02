package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.PointEntity
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.IPointService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.PointService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/points")
class PointController (
    private val pointService: IPointService
){
    @GetMapping("/getAll")
    fun getPoints() : ResponseEntity<List<PointEntity>>{
        return ResponseEntity.ok(pointService.getAllPoints())
    }

    @GetMapping("/get/{pointId}")
    fun getPoints(@PathVariable("pointId") pointId : String) : ResponseEntity<PointEntity?>{
        val foundPoint = pointService.getPointById(pointId)?:
            return ResponseEntity.notFound().build()
        return ResponseEntity.ok(foundPoint)
    }

    @PutMapping("/capture/{pointId}")
    fun capturePoint(@PathVariable("pointId") pointId : String, @RequestBody newOwner:UserEntity) : ResponseEntity<Boolean>{
        if(pointService.capturePoint(pointId, newOwner)){
            return ResponseEntity.ok().body(true)
        }
        return ResponseEntity.notFound().build()
    }

    @PostMapping("/addNewPoint")
    fun addNewPoint(@RequestBody body:PointEntity) : ResponseEntity<PointEntity>{
        return ResponseEntity.ok().body(pointService.addPoint(body))
    }



}