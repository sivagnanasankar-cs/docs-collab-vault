package com.doccollab.auth.servlets;

import com.doccollab.auth.dao.OAuthClientDAO;
import com.doccollab.auth.models.OAuthClient;
import com.doccollab.auth.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TokenServlet extends HttpServlet {

    private final JwtUtil jwtUtil = new JwtUtil();
    private final OAuthClientDAO oAuthClientDAO = new OAuthClientDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String grantType = req.getParameter("grant_type");

        if ("client_credentials".equals(grantType)) {
            String[] credentials = extractCredentials(req.getHeader("Authorization"));
            String clientId = credentials[0];
            String clientSecret = credentials[1];

            // In a real application, you would look up the client by clientId
            // and verify the clientSecret against a hashed version in the database.
            // For this example, we'll assume the client is valid.

            Map<String, Object> claims = new HashMap<>();
            // Add any necessary claims for the client

            String token = jwtUtil.generateToken(clientId, claims);

            Map<String, String> response = new HashMap<>();
            response.put("access_token", token);
            response.put("token_type", "bearer");

            resp.setContentType("application/json");
            resp.getWriter().write(objectMapper.writeValueAsString(response));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Unsupported grant type");
        }
    }

    private String[] extractCredentials(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.toLowerCase().startsWith("basic ")) {
            String base64Credentials = authorizationHeader.substring("Basic ".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded);
            // credentials = username:password
            return credentials.split(":", 2);
        }
        return new String[0];
    }
}