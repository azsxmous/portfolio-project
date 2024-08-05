package org.project.portfolio.controller

import org.project.portfolio.domain.auth.CreateAccessToeknRequest
import org.project.portfolio.domain.auth.CreateAccessTokenResponse
import org.project.portfolio.domain.auth.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TokenApiController (
    private val tokenService : TokenService
){
    @PostMapping("/api/token")
    fun createNewAccessToken(@RequestBody request: CreateAccessToeknRequest) : ResponseEntity<CreateAccessTokenResponse> {
        val newAccessToken : String = tokenService.createNewAccessToken(request.refreshToken)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(CreateAccessTokenResponse(newAccessToken))
    }
}