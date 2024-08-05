package org.project.portfolio.domain.user

import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
){
    fun findById(userId: Long?): User? {
        return userRepository.findById(userId!!) // '!!'??
            .orElseThrow { IllegalArgumentException("Unexpected user") }
    }
}