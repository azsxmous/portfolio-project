package org.project.portfolio.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로퍼티값을 가져와서 사용하는 어노테이션
class JwtProperties(
    val issuer: String,
    val secretKey: String
) {
}
