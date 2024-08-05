package org.project.portfolio.domain.auth

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId:Long?) : Optional<RefreshToken?>?
    fun findByRefreshToken(refreshToke:String?) : RefreshToken?

}