package com.doccollab.auth.servlets;

import com.doccollab.auth.models.AuthenticatedUser;
import com.doccollab.auth.utils.JwtUtil;
import com.doccollab.permissions.services.PermissionEvaluator;
import com.doccollab.redis.RedisUtil;
import com.doccollab.redis.cache.TokenService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuthFilter implements Filter {

    private JwtUtil jwtUtil;
    private TokenService tokenService;
    private PermissionEvaluator permissionEvaluator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.jwtUtil = new JwtUtil();
        this.tokenService = new TokenService(RedisUtil.getJedisPool());
        this.permissionEvaluator = new PermissionEvaluator();
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

                String resource = httpRequest.getRequestURI();
                String scope = getScopeFromRequest(httpRequest);

                if (permissionEvaluator.hasPermission(authenticatedUser, resource, scope)) {
                    chain.doFilter(request, response);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
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

    private String getScopeFromRequest(HttpServletRequest request) {
        // This is a very simplified way to determine the required scope.
        // In a real application, you would have a more robust mechanism
        // for mapping endpoints to required scopes.
        if (request.getMethod().equalsIgnoreCase("POST")) {
            return "write";
        }
        return "read";
    }
}