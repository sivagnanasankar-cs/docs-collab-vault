package com.doccollab.permissions.services;

import com.doccollab.auth.models.AuthenticatedUser;
import com.doccollab.permissions.dao.PermissionDAO;
import com.doccollab.permissions.models.Permission;
import com.doccollab.user.dao.UserDAO;
import com.doccollab.user.models.User;

import java.util.List;

public class PermissionEvaluator {

    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final UserDAO userDAO = new UserDAO();

    public boolean hasPermission(AuthenticatedUser authenticatedUser, String resource, String scope) {
        User user = userDAO.findById(Long.parseLong(authenticatedUser.getSubject()));
        if (user == null) {
            return false;
        }

        List<Permission> permissions = permissionDAO.findByUser(user.getId());

        for (Permission permission : permissions) {
            if (permission.getScopes().contains(scope)) {
                return true;
            }
        }

        return false;
    }
}