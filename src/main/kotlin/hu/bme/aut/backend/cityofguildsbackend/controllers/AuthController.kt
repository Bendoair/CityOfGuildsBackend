package hu.bme.aut.backend.cityofguildsbackend.controllers

import hu.bme.aut.backend.cityofguildsbackend.domain.AuthDTO
import hu.bme.aut.backend.cityofguildsbackend.domain.TokenDTO
import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.services.IAuthService
import hu.bme.aut.backend.cityofguildsbackend.services.impl.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: IAuthService
) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(@RequestBody authDTO: AuthDTO): ResponseEntity<TokenDTO> {
        val resultToken = authService.authenticate(authDTO.email, authDTO.password)
        //Login failed
        if (resultToken == "") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(TokenDTO(resultToken))
    }

    @PostMapping("/register")
    fun register(@RequestBody authDTO: AuthDTO): ResponseEntity<TokenDTO> {
        val resultToken = authService.register(authDTO.email, authDTO.password)
        if (resultToken == "") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
        return ResponseEntity.ok(TokenDTO(resultToken))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody tokenDTO: TokenDTO): ResponseEntity<UserEntity> {
        logger.info("Trying to authenticate $tokenDTO")
        val user = authService.getUserByToken(tokenDTO.token) ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        return ResponseEntity.ok(user)
    }


}