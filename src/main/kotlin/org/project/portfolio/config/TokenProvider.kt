package org.project.portfolio.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.project.portfolio.domain.user.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.Collections
import java.util.Date

@Service
class TokenProvider (
    private val jwtProperties: JwtProperties
){
    fun generateToken(user: User?, expriredAt : Duration) : String{
        val now = Date()
        return makeToken(Date(now.time + expriredAt.toMillis()), user)
    }

    private fun makeToken(expiry: Date, user: User?): String {
        val now = Date()
        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
            .setIssuer(jwtProperties.issuer) // 내용 iss : properties 파일에서 설정한 issuer 값
            .setIssuedAt(now) // 내용 iat : 현재 시간
            .setExpiration(expiry) // 내용 exp : expiry 멤버 변수값
            .setSubject(user?.email) // 내용 sub : 유저의 email
            .claim("id", user?.id) // 클레임 id : 유저의 id
            .signWith(SignatureAlgorithm.HS256, jwtProperties.secretKey) // 서명 : 비밀키와 함께 해시값을 HS256 방식으로 암호화
            .compact()
    }

    fun validToken(token:String?) : Boolean {
        try {
            Jwts.parser()
                .setSigningKey(jwtProperties.secretKey) // 비밀값으로 복호화
                .parseClaimsJws(token)
            return true
        } catch (e : Exception) {
            return false
        }
    }

    // 토큰 기반으로 인증 정보를 가져옴
    fun getAuthentication(token : String?) : Authentication {
        val claims : Claims = getClaims(token)
        val authorities : Set<SimpleGrantedAuthority> = Collections.singleton(SimpleGrantedAuthority("ROLE_USER"))
        return UsernamePasswordAuthenticationToken(org.springframework.security.core.userdetails.User(claims.subject, "", authorities), token, authorities)
    }

    // 토큰 기반으로 유저 ID를 가져옴
    fun getUserId(token : String) : Long {
        val claims : Claims = getClaims(token)
        return claims.get("id", Long::class.java)
    }

    // 클레임 조회
    private fun getClaims(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(jwtProperties.secretKey)
            .parseClaimsJws(token)
            .body
    }
}