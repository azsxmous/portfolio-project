package org.project.portfolio.domain.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name="refreshtoken")
class RefreshToken (
    @Column(name="user_id", nullable=false, unique=true)
    val userId:Long,
    @Column(name="refreshToken", nullable=false)
    var refreshToken: String,
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long ?= null

    public fun update(newRefreshToken: String) : RefreshToken {
        this.refreshToken = newRefreshToken;
        return this;
    }
}