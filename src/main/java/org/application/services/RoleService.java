package org.application.services;

import org.application.models.Role;

import java.util.List;

public interface RoleService {
    Role getRole(int id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    Role createRole(Role role);
    void updateRole(Role role);
    void deleteRole(int id);
}
