package org.project.portfolio.controller

import org.project.portfolio.domain.auth.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class SigninApiController @Autowired constructor(
    private val signinService: SigninService,
    private val userContextHolder: UserContextHolder
) {
    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest): ResponseEntity<SigninResponse> {
        return ResponseEntity.ok().body(signinService.signin(signinRequest))
    }

//    @PostMapping("/refresh_token")
//    fun refreshToken(
//        @RequestParam("grant_type") grantType: String
//    ): ResponseEntity<String> {
//        if(grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH) {
//            throw IllegalArgumentException("grant_type 없음")
//        }
//        return userContextHolder.email?.let {
//            ResponseEntity.status(HttpStatus.OK).body(JWTUtil.createToken(it))
//        }  ?: throw IllegalArgumentException("사용자 정보 없음")
//
//    }
}