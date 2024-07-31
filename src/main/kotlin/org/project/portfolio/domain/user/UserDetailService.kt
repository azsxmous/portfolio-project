package org.project.portfolio.domain.user

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service // 스프링 시큐리티에서 사용자 정보를 가져오는 인터페이스
class UserDetailService : UserDetailsService {
    private val userRepository: UserRepository? = null

    // 사용자 이름(email)으로 사용자의 정보를 가져오는 메서드
    override fun loadUserByUsername(email: String): UserDetails {
        return (userRepository!!.findByEmail(email)
            ?: throw  IllegalArgumentException(email)) as UserDetails
    }
}