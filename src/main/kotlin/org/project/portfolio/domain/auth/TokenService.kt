package org.project.portfolio.domain.auth

import org.project.portfolio.config.TokenProvider
import org.project.portfolio.domain.user.User
import org.project.portfolio.domain.user.UserService
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class TokenService (
    val tokenProvider: TokenProvider,
    val refreshTokenService: RefreshTokenService,
    val userService: UserService
){
    fun createNewAccessToken(refreshToken : String) : String {
        // 토큰 유효성 검사에 실패하면 예외 발생
        if(!tokenProvider.validToken(refreshToken)){
            throw IllegalArgumentException("Unexpected token")
        }

        val userId = refreshTokenService.findByRefreshToken(refreshToken)?.userId
        val user = userService.findById(userId)

        return tokenProvider.generateToken(user, Duration.ofHours(2))
    }
}