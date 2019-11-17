package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AccountDTO {

    private int id;
    private String name;
    private List<Integer> userIds;


    public AccountDTO() {
    }

    public AccountDTO(String name) {
        this.name = name;
    }

    public AccountDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccountDTO(int id, String name, List<Integer> userIds) {
        this.id = id;
        this.name = name;
        this.userIds = userIds;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("user_ids")
    public List<Integer> getUserIds() {
        return userIds;
    }


}
