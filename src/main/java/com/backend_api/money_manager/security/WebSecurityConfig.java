package com.backend_api.money_manager.security;

import com.backend_api.money_manager.entity.Role;
import com.backend_api.money_manager.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    private final String auth = "/api/v1/auth/**";
    private final String user = "/api/v1/user/**";
    private final String admin = "/api/v1/admin/**";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(auth)
                        .permitAll()
                        .requestMatchers(user)
                        .hasAnyAuthority(Role.USER.name(),Role.ADMIN1.name())
                        .requestMatchers(admin)
                        .hasAuthority(Role.ADMIN1.name())
                        .anyRequest()
                        .permitAll()
                )
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .accessDeniedHandler(customAccessDeniedException)
//                        .authenticationEntryPoint(customAuthenticationEntryPoint)
//                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).apply(new CorsConfigurer<>());

        return http.build();
    }

}
