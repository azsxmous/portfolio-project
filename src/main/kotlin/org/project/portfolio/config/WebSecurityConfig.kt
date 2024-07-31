package org.project.portfolio.config

import org.project.portfolio.domain.user.UserDetailService
import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.annotation.web.configurers.*
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class WebSecurityConfig {
    // 스프링 시큐리티 기능 비활성화
    @Bean
    fun configure(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                //.requestMatchers(AntPathRequestMatcher("/**"));
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers("/static/**")
        }
    }
    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    @Throws(java.lang.Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        return http.authorizeHttpRequests(
            Customizer { auth: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry ->
                auth // 허가된 url 리스트
                    .requestMatchers("/api/signin", "/api/users").permitAll()
                    .anyRequest().authenticated()
            }
        ).formLogin { formLogin: FormLoginConfigurer<HttpSecurity?> ->
            formLogin.loginPage("/login")
                .defaultSuccessUrl("/articles")
        }.logout { logout: LogoutConfigurer<HttpSecurity?> ->
            logout.logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
        }
        .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() } // csrf 비활성화 (실습에서만)
        .build()
    }

    // 인증 관리자 관련 설정
    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        http: HttpSecurity, bCryptPasswordEncoder: BCryptPasswordEncoder?,
        userDetailService: UserDetailService
    ): AuthenticationManager {
        val authManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authManagerBuilder.userDetailsService<UserDetailsService>(userDetailService)
            .passwordEncoder(bCryptPasswordEncoder)
        return authManagerBuilder.build()
    }


}