package org.project.portfolio.domain.auth

data class SignupRequest (
    val email: String,
    val name: String,
    val password: String,
    val phone: String
)