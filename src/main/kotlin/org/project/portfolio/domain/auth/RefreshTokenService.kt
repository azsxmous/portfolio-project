package org.project.portfolio.domain.auth

import org.springframework.stereotype.Service
import java.util.*

@Service
class RefreshTokenService (
    private val refreshTokenRepository : RefreshTokenRepository
){
    fun findByRefreshToken(refreshToken: String?): RefreshToken? {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
            //?:throw IllegalArgumentException("Unexpected token")
    }
}