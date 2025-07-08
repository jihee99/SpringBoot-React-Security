package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** 패스워드 인코더 */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** Spring Security 메인 설정 */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ---- 기본 보안 옵션 ----
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})                // CorsConfigurationSource @Bean 사용
                // ---- URL-별 권한 ----
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("A")
                        .requestMatchers("/user/**").authenticated()
                        .anyRequest().permitAll()
                )
                // ---- 폼 로그인 ----
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .usernameParameter("userId")
                        .defaultSuccessUrl("/loginOk", true)   // true: 항상 이동
                )
                // ---- 로그아웃 ----
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/logoutOk")
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    /** CORS 공통 설정 */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);                 // 쿠키 포함 허용
        config.addAllowedOrigin("http://localhost:3000"); // React dev-server
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);  // 전역 적용
        return source;
    }
}