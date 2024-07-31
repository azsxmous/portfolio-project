package org.project.portfolio.domain.auth

import org.project.portfolio.common.CommonException
import org.project.portfolio.domain.user.User
import org.project.portfolio.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class SigninService @Autowired constructor(
        private val userRepository: UserRepository
) {
    fun signin(signinRequest: SigninRequest): SigninResponse {
        val user = userRepository
                .findByEmail(signinRequest.email.lowercase())
                ?: throw CommonException("로그인 정보를 확인해주세요.")
        if(isNotValidPassword(signinRequest.password, user.password)){
            throw CommonException("로그인 정보를 확인해주세요.")
        }
        return responseWithTokens(user)
    }

    private fun isNotValidPassword(plain: String, hashed: String) = BCrypt.checkpw(plain, hashed).not()

    private fun responseWithTokens(user: User) = user.id?.let { userId ->
        SigninResponse(
                JWTUtil.createToken(user.email),
                JWTUtil.createRefreshToken(user.email),
                user.name,
                userId
        )
    } ?: throw IllegalStateException("user.id 없음")
}