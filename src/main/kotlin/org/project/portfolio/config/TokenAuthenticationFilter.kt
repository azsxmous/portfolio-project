package org.project.portfolio.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

public class TokenAuthenticationFilter : OncePerRequestFilter() {
    val tokenProvider: TokenProvider? = null
    val HEADER_AUTHORIZATION = "Authorization"
    val TOKEN_PREFIX = "Bearer"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 요청 헤더의 Authorization 키의 값 조회
        var authorizationHeader = request.getHeader(HEADER_AUTHORIZATION)
        // 가져온 값에서 접두사 제거
        var token = getAccessToken(authorizationHeader)
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
        if(tokenProvider?.validToken(token) == true) {
            val authentication : Authentication = tokenProvider.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun getAccessToken(authenticationHeader: String) : String? {
        if(authenticationHeader != null && authenticationHeader.startsWith(TOKEN_PREFIX)) {
            return authenticationHeader.substring(TOKEN_PREFIX.length)
        }
        return null
    }
}