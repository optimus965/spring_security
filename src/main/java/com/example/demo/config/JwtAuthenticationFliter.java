package com.example.demo.config;

import com.example.demo.token.Token;
import com.example.demo.token.TokenRepository;
import com.example.demo.Repository.userRepository;
import com.example.demo.user.User;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFliter extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final userRepository userRepository1;
    Logger logger = Logger.getLogger(JwtAuthenticationFliter.class.getName());
    @Override

    protected void doFilterInternal(@NotNull  HttpServletRequest request,
                                    @Nonnull  HttpServletResponse response,
                                    @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        // to extract the userEmail from jwt Token
        userEmail = jwtService.extractUsername(jwt);
        if(userEmail  != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var TokenPresent= tokenRepository.findByToken(jwt).map(t->!t.isExpired()).orElse(false);
            User user1 = userRepository1.findByEmail(userEmail).orElseThrow(()->new UsernameNotFoundException("user name not found"));
            boolean isToken = false;
            List<Token>  list = tokenRepository.findByUserId(user1.getId());
            for(Token c:list) {
                logger.info(c.token);
                if (c.token.equals(jwt)) {
                    isToken = true;
                    break;
                }
            }
//            if(jwtService.isTokenValid(jwt,userDetails) && TokenPresent) {
            if(isToken && TokenPresent) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
