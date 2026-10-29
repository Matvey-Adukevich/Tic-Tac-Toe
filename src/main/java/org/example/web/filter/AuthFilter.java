package org.example.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.service.AuthService;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.UUID;

public class AuthFilter extends GenericFilterBean {
    private final AuthService authService;

    public AuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String path = httpServletRequest.getRequestURI();
        if (path.equals("/") || path.equals("/index.html") || 
            path.equals("/style.css") || path.equals("/script.js") ||
            path.startsWith("/static/") || path.equals("/auth/register") || 
            path.equals("/auth/login") || path.startsWith("/game/") ||
            path.startsWith("/user/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        String auth = httpServletRequest.getHeader("Authorization");
        if(auth == null){
            httpServletResponse.setStatus(401);
            return;
        }
        String authString = auth.replace("Basic ", "");

        UUID uuid = authService.login(authString);
        if(uuid == null){
            httpServletResponse.setStatus(401);
            return;
        }
        chain.doFilter(request, response);


    }
}
