package org.project.portfolio.domain.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.Date

object JWTUtil {
    private const val ISSUER = "ISSUER"
    private const val SUBJECT = "Auth"
    private const val EXPIRE_TIME = 60L * 60 * 1 * 1000 // 1시간
    private const val REFRESH_EXPIRE_TIME = 60L * 60 * 3 * 1000 // 3시간

    private val SECRET = "your-secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET)

    private val refreshSecret = "your-refresh-secret"
    private val refreshAlgorithm: Algorithm = Algorithm.HMAC256(refreshSecret)

    fun createToken(email: String) = JWT.create()
            .withIssuer(ISSUER)
            .withSubject(SUBJECT)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + EXPIRE_TIME))
            .withClaim(JWTClaims.EMAIL, email)
            .sign(algorithm)

    fun createRefreshToken(email: String) = JWT.create()
            .withIssuer(ISSUER)
            .withSubject(SUBJECT)
            .withIssuedAt(Date())
            .withExpiresAt(Date(Date().time + REFRESH_EXPIRE_TIME))
            .withClaim(JWTClaims.EMAIL, email)
            .sign((algorithm))

    // 토큰의 유효성을 검증하고 DecodedJWT 객체 반환
    fun verify(token: String): DecodedJWT =
        JWT.require(algorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun verifyRefresh(token: String): DecodedJWT =
        JWT.require(refreshAlgorithm)
            .withIssuer(ISSUER)
            .build()
            .verify(token)

    fun extractEmail(jwt: DecodedJWT): String =
        jwt.getClaim(JWTClaims.EMAIL).asString()

    object JWTClaims {
        const val EMAIL = "email"
    }

}