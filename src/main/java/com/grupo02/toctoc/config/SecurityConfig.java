package com.grupo02.toctoc.config;

import com.grupo02.toctoc.config.filter.AuthenticationFilter;
import lombok.AllArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationFilter jwtAuthFilter;
    //private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req.requestMatchers(HttpMethod.POST,"/post/**").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.GET,"/post/all" ).permitAll()
                                .requestMatchers(HttpMethod.POST,"/comments" ).hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST,"/users").hasAnyAuthority("ROLE_USER")
                                .requestMatchers(HttpMethod.POST,"/users/reset-password").permitAll()
                                .requestMatchers(HttpMethod.POST,"/users/refresh").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/users").hasAnyAuthority("ROLE_USER")
                                .requestMatchers("/users/login").permitAll()
                                .requestMatchers("/users/signup").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/ping").permitAll()
                                .anyRequest().authenticated()
                )
                //.authenticationProvider(authenticationProvider)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);;

        return http.build();
    }

}
