package org.project.portfolio.domain.auth

import org.project.portfolio.domain.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * 토큰 유효성 검증을 위한 Service
 */
@Service
class UserContextHolder @Autowired constructor(
    private val userRepository: UserRepository
){
    private val userHolder = ThreadLocal.withInitial {
        UserHolder()
    }

    val id: Long? get() = userHolder.get().id
    val name: String? get() = userHolder.get().name
    val email: String? get() = userHolder.get().email

    fun set(email: String) = userRepository
        .findByEmail(email)?.let {user ->
            this.userHolder.get().apply {
                this.id = user.id
                this.name = user.name
                this.email = user.email
            }.run(userHolder::set)
        }

    fun clear() {
        userHolder.remove()
    }

    class UserHolder {
        var id: Long? = null
        var name: String? = null
        var email: String? = null
    }
}