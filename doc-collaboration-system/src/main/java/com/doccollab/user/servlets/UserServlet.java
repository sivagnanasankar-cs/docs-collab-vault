package com.doccollab.user.servlets;

import com.doccollab.organization.dao.OrganizationDAO;
import com.doccollab.organization.models.Organization;
import com.doccollab.user.models.User;
import com.doccollab.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final OrganizationDAO organizationDAO = new OrganizationDAO();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Long orgId = Long.parseLong(req.getPathInfo().split("/")[1]);

        Organization organization = organizationDAO.findById(orgId);

        User user = userService.createUser(email, name, password, organization);

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(user));
    }
}