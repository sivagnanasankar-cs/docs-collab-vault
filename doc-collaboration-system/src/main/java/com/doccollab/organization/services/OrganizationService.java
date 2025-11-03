package com.doccollab.organization.services;

import com.doccollab.organization.dao.OrganizationDAO;
import com.doccollab.organization.models.Organization;
import com.doccollab.user.dao.UserDAO;
import com.doccollab.user.models.User;

public class OrganizationService {

    private final OrganizationDAO organizationDAO = new OrganizationDAO();
    private final UserDAO userDAO = new UserDAO();

    public Organization createOrganization(String name, String domain) {
        Organization organization = new Organization();
        organization.setName(name);
        organization.setDomain(domain);
        organization.setCreatedAt(System.currentTimeMillis());
        organizationDAO.save(organization);
        return organization;
    }

    public void addUserToOrganization(User user, Organization organization) {
        user.setOrganization(organization);
        userDAO.update(user);
    }
}