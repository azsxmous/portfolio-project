package org.project.portfolio.domain.auth

import org.project.portfolio.common.CommonException
import org.project.portfolio.domain.user.User
import org.project.portfolio.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


/**
 * 이메일 - 이메일 형식에 맞는지 검증
 * 휴대폰 번호 - 숫자와 하이폰으로 구성된 형식 검증
 * 작성자 - 아이디 대소문자 및 한글 이름 검증
 * 비밀번호 - 대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검증
 */
@Service
class SignupService @Autowired constructor(
    private val userRepository: UserRepository
){
    @Bean
    fun bCryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
    fun signup(signupRequest: SignupRequest) {
        validateRequest(signupRequest)
        registerUser(signupRequest)
    }

    private fun validateRequest(signupRequest: SignupRequest) {
        validateEmail(signupRequest.email)
        validName(signupRequest.name)
        validatePhone(signupRequest.phone)
        validatePassword(signupRequest.password)
    }

    private fun validateEmail(email: String) {
        // 이메일 형식에 맞는지 검증
        val isNotValidEmail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$"
                .toRegex(RegexOption.IGNORE_CASE)
                .matches(email)
                .not()
        if(isNotValidEmail) {
            throw CommonException("이메일 형식이 올바르지 않습니다.")
        }
    }

    private fun validName(name: String) {
        // 아이디 대소문자 및 한글 이름 검즘
        if(name.trim().length !in 2..20) {
            throw CommonException("이름은 2자 이상 20자 이하여야 합니다.")
        }
    }

    private fun validatePhone(phone: String) {
        // 숫자와 하이폰으로 구성된 형식 검즘
        val isNotValidPhone = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}\$"
                .toRegex(RegexOption.IGNORE_CASE)
                .matches(phone)
                .not()
        if(isNotValidPhone) {
            throw CommonException("전화번호 형식이 올바르지 않습니다.")
        }
    }

    private fun validatePassword(password: String) {
        // 비밀번호 - 대소문자, 숫자 5개 이상, 특수문자 포함 2개 이상 검증
        // ^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$
        // 숫자만 추출 : str.replace("[^0-9]".toRegex(), "")
        val pwPattern1 =
                "^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z[0-9]]{8,20}$" // 영문, 숫자
        val pwPattern2 =
                "^(?=.*[0-9])(?=.*[$@$!%*#?&.])[[0-9]$@$!%*#?&.]{8,20}$" // 숫자, 특수문자
        val pwPattern3 =
                "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$" // 영문, 숫자, 특수문자

        val isValidPassword = pwPattern1.toRegex().matches(password) || pwPattern2.toRegex().matches(password) || pwPattern3.toRegex().matches(password)

        if(!(isValidPassword && password.replace("[^0-9]".toRegex(), "").length > 4))
            throw CommonException("비밀번호 형식이 올바르지 않습니다.")
    }
// .password(bCryptPasswordEncoder.encode(dto.getPassword()))
    private fun registerUser(signupRequest: SignupRequest) =
        with(signupRequest) {
            val hashedPassword = bCryptPasswordEncoder().encode(password)
            val user = User(email, name, hashedPassword, phone)
            userRepository.save(user)
    }

}