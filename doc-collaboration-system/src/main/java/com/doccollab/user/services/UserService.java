package com.doccollab.user.services;

import com.doccollab.auth.utils.PasswordUtil;
import com.doccollab.organization.models.Organization;
import com.doccollab.user.dao.UserDAO;
import com.doccollab.user.models.User;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public User createUser(String email, String name, String password, Organization organization) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPasswordHash(PasswordUtil.hashPassword(password));
        user.setOrganization(organization);
        user.setCreatedAt(System.currentTimeMillis());
        userDAO.save(user);
        return user;
    }
}