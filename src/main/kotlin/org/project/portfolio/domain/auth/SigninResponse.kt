package org.project.portfolio.domain.auth

data class SigninResponse (
    val token: String,
    val refreshToken: String,
    val userName: String,
    val uerId: Long
)