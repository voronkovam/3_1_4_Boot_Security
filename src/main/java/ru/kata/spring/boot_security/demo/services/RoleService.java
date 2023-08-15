package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    public List<Role> getListRoles();

    public Role getRole(String name);

    public Role getRoleById(Long id);

    public void addRole(Role role);
}
