package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserADTO {

    private int id;
    private String name;
    private int accountId;

    public UserADTO() {
    }

    public UserADTO(int id, String name, int accountId) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("account_id")
    public int getAccountId() {
        return accountId;
    }
}
