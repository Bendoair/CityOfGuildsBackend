package hu.bme.aut.backend.cityofguildsbackend.services

import io.jsonwebtoken.Jwts
import java.util.*

interface ITokenService {

    fun generateToken(email: String): String

    fun validateToken(token: String): Boolean

    fun getEmailFromToken(token: String): String?
}