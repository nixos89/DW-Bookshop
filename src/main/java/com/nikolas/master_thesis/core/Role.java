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
    @NotNull private String name;
    private Set<User> users;

    public Role(Long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

}
