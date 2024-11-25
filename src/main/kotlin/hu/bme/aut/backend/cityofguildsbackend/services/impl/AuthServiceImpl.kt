package hu.bme.aut.backend.cityofguildsbackend.services.impl

import hu.bme.aut.backend.cityofguildsbackend.domain.UserEntity
import hu.bme.aut.backend.cityofguildsbackend.repositories.IUserRepository
import hu.bme.aut.backend.cityofguildsbackend.services.IAuthService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.crypto.spec.SecretKeySpec


@Service
class AuthServiceImpl(
    private val userRepository: IUserRepository,
    private val tokenService: TokenService,
) : IAuthService {
    private val passwordEncoder : PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    override fun authenticate(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
        if (user != null && passwordEncoder.matches(password, user.password)) {
            //Generate token for user and return it
            return tokenService.generateToken(user.email)
        }
        //Return nothing
        return ""
    }

    override fun register(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
        if (user != null) {
            //user is already registered
            return ""
        }
        //Hash password
        val hashedPassword = passwordEncoder.encode(password)
        val newUser = UserEntity(
            id = "",
            email = email,
            password = hashedPassword,
            numberOfPoints = 0,
            isDeveloper = false
        )
        userRepository.save(newUser)

        //Generate token and return it
        return tokenService.generateToken(email = email)

    }

    override fun getUserByToken(token: String): UserEntity? {
        //Return null if token is not valid
        if(!tokenService.validateToken(token)){
            println("Token is invalid token:$token")
            return null
        }
        //Find user
        val user = userRepository.findByEmail(tokenService.getEmailFromToken(token)?:"")
        return user?.copy(password = "Haha, no")
    }
}