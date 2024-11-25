package hu.bme.aut.backend.cityofguildsbackend.services.impl

import hu.bme.aut.backend.cityofguildsbackend.services.ITokenService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService : ITokenService {

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // Replace with environment-based key
    private val expirationTimeInMs = 24 * 60 * 60 * 1000 // 24 hours

    override fun generateToken(email: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTimeInMs)

        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(secretKey)
            .compact()
    }
    override fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (ex: Exception) {
            false
        }
    }
    override fun getEmailFromToken(token: String): String? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (ex: Exception) {
            null
        }
    }
}