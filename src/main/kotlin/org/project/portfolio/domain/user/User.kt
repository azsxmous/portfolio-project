package org.project.portfolio.domain.user

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name="users")
// email, phone, 이름, ID, 비밀번호
class User (var email: String,
            var name: String,
            var password: String,
            var phone: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}