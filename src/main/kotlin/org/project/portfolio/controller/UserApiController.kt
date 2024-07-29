package org.project.portfolio.controller

import org.project.portfolio.domain.auth.SignupRequest
import org.project.portfolio.domain.auth.SignupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
        private val signupService: SignupService
){
    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest): ResponseEntity<String> {
        signupService.signup(signupRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body("success")
    }

}