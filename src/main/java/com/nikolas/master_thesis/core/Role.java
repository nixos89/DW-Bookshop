package com.nikolas.master_thesis.core;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users"})
public class Role {

    private Long roleId;

    @ColumnName("role_name")
    @NotNull
    private String role_name;
    private Set<User> users;// TODO: research HOW TO implement One-To-Many relationship with JDBi3

    public Role(Long roleId, String role_name) {
        this.roleId = roleId;
        this.role_name = role_name;
    }

}
