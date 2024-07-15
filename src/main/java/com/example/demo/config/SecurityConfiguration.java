package com.example.demo.config;

import com.example.demo.user.Permissions;
import com.example.demo.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFliter jwtAuthenticationFliter;
    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

//        httpSecurity.csrf().disable().authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll().
//        httpSecurity.csrf().disable().authorizeRequests().requestMatchers("/api/v1/auth/**").permitAll()
//                        .and().authorizeRequests().anyRequest().authenticated().and().sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider).addFilterBefore(jwtAuthenticationFliter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r->r.requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/admin/**").hasAuthority(Permissions.ADMIN_READ.name())
                        .requestMatchers("/api/v1/management/**").hasAuthority(Permissions.MANAGER_READ.name())
                        .anyRequest().authenticated()).sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFliter, UsernamePasswordAuthenticationFilter.class);
        return  httpSecurity.build();
    }




}
