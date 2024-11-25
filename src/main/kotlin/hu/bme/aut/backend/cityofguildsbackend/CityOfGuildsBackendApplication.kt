package hu.bme.aut.backend.cityofguildsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class CityOfGuildsBackendApplication

fun main(args: Array<String>) {
    runApplication<CityOfGuildsBackendApplication>(*args)
}
