package com.doccollab.auth.servlets;

import com.doccollab.auth.models.AuthenticatedUser;
import com.doccollab.auth.utils.JwtUtil;
import com.doccollab.redis.RedisUtil;
import com.doccollab.redis.cache.TokenService;
import redis.clients.jedis.JedisPool;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthFilter implements Filter {

    private JwtUtil jwtUtil;
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.jwtUtil = new JwtUtil();
        this.tokenService = new TokenService(RedisUtil.getJedisPool());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String token = authHeader.substring("Bearer ".length()).trim();
            String subject = tokenService.getSubject(token);

            if (subject != null && jwtUtil.validateToken(token, subject)) {
                AuthenticatedUser authenticatedUser = new AuthenticatedUser(subject);
                httpRequest.setAttribute("authenticatedUser", authenticatedUser);
                chain.doFilter(request, response);
            } else {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }
}