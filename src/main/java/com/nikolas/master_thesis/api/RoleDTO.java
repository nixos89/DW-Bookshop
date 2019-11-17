package com.nikolas.master_thesis.api;

import java.util.Set;

public class RoleDTO {

    private Long id;
    private String name;
    private Set<UserADTO> users;

    public RoleDTO() {
    }

    public RoleDTO(String name) {
        this.name = name;
    }

    public RoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<UserADTO> getUsers() {
        return users;
    }
}
