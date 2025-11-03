package com.doccollab.organization.servlets;

import com.doccollab.organization.models.Organization;
import com.doccollab.organization.services.OrganizationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrganizationServlet extends HttpServlet {

    private final OrganizationService organizationService = new OrganizationService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String domain = req.getParameter("domain");

        Organization organization = organizationService.createOrganization(name, domain);

        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(organization));
    }
}