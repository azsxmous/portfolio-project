package org.project.portfolio

import io.mockk.mockk
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.project.portfolio.domain.auth.JWTUtil
import org.project.portfolio.domain.auth.SignupRequest
import org.project.portfolio.domain.auth.SignupService
import org.project.portfolio.domain.user.User
import org.project.portfolio.domain.user.UserRepository
import kotlin.test.assertEquals


class SignupServiceTest {
    private val userRepository:UserRepository = mockk(relaxed = true)
    private val signupService: SignupService = SignupService(userRepository);

    @DisplayName("회원 가입 검증")
    @Test
    fun signupTest() {
        val url = "/api/v1/user"
        val email = "abcde@ab.com"
        val name = "akaka"
        val phone = "010-4556-7878"
        val password = "abcde12345!"
        val signupRequest = SignupRequest(email, name, password, phone)
        val user = User(email, name, phone, password)

        //userRepository.save(user)
        signupService.signup(signupRequest)

    }

    @DisplayName("JWT 검증")
    @Test
    fun jwtTest() {
        //given
        val userEmail = "user@email.com"
        val token = JwtFactory(
            claims = userEmail
        ).createToken()
        //when
        val verifyuser  = JWTUtil.verify(token) // verify : decode된 token 값
        val userByToken:String = JWTUtil.extractEmail(verifyuser)
        //then
        assertEquals(userByToken, userEmail)
    }
}