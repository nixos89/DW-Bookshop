package com.nikolas.master_thesis.core;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public class Role {

    private Long roleId;

    @ColumnName("role_name")
    @NotNull
    private String role_name;
    private Set<User> users;// TODO: research HOW TO implement One-To-Many relationship with JDBi3

    public Role() {
    }

    public Role(String role_name) {
        this.role_name = role_name;
    }

    public Role(Long roleId, String role_name) {
        this.roleId = roleId;
        this.role_name = role_name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId.equals(role.roleId) &&
                role_name.equals(role.role_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, role_name);
    }
}
