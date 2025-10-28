package com.bugra.security;

import com.bugra.exceptions.JwtException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtErrorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            Object error = request.getAttribute("jwt_error");
            if (error != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                Map<String, Object> body = new HashMap<>();
                body.put("success", false);
                body.put("message", "Unauthorized");
                body.put("error", error);
                body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
                body.put("timestamp", LocalDateTime.now().toString());

                ObjectMapper mapper = new ObjectMapper();
                mapper.writeValue(response.getOutputStream(), body);
                return;
            }
            filterChain.doFilter(request, response);
    }
}
