package com.ecommerce.backend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.backend.entity.User;
import com.ecommerce.backend.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            // ✅ Validate token first
            if (jwtUtil.validateToken(token)) {

                String email = jwtUtil.extractEmail(token);

                // ✅ Fetch user from DB
                User user = userRepository.findByEmail(email).orElse(null);

                if (user != null) {

                    // ✅ Set role as authority
                    List<SimpleGrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority(user.getRole()));

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    authorities
                            );

                    // ✅ Set authentication in context
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }

        // ✅ Continue filter chain
        filterChain.doFilter(request, response);
    }
}