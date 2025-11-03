package com.doccollab.permissions.services;

import com.doccollab.permissions.dao.PermissionDAO;
import com.doccollab.permissions.dao.RoleDAO;
import com.doccollab.permissions.models.Permission;
import com.doccollab.permissions.models.Role;
import com.doccollab.user.models.User;

public class PermissionService {

    private final PermissionDAO permissionDAO = new PermissionDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    public void assignRoleToUser(User user, Role role) {
        // In a real application, you would have a UserRole mapping table.
        // For simplicity, we'll just add a permission that links the user to the role.
        Permission permission = new Permission();
        permission.setGranteeType("USER");
        permission.setGranteeId(user.getId());
        permission.setResourceType("ROLE");
        permission.setResourceId(role.getId());
        permission.setScopes("member"); // or some other indicator of membership
        permissionDAO.save(permission);
    }
}